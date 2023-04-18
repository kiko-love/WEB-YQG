package com.yqg.websocket;

import com.alibaba.fastjson.JSONObject;
import com.yqg.utils.ResultUtils;
import com.yqg.vo.QrLogin;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import static com.yqg.R.NomalEnum.LOGIN_KEY;

/**
 * @author KIKO
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/api/websocket/login/{uid}")
public class WebSocketLoginServer {
    @Autowired
    private QrLogin qrLoginTmp;
    private static QrLogin qrLogin;

    @Autowired
    private StringRedisTemplate redisTemplateTmp;
    private static RedisTemplate redisTemplate;
    @PostConstruct
    public void init(){
        redisTemplate = redisTemplateTmp;
        qrLogin = qrLoginTmp;
    }
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketLoginServer> webSocketSet
            = new CopyOnWriteArraySet<WebSocketLoginServer>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收uid
     */
    private String uid = "";

    /**
     * 等待二维码扫码结果的长连接
     *
     * @param uid
     * @return
     */
    public Callable<Map<String, Object>> getResponse(String uid){
        // 非阻塞
        Callable<Map<String, Object>> callable = () -> {
            Map<String, Object> result = new HashMap<>(16);
            result.put("uid", uid);
            try {
                ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
                String user = opsForValue.get(LOGIN_KEY + uid);
                // 长时间不扫码，二维码失效。需重新获二维码
                if (user == null) {
                    result.put("success", false);
                    result.put("stats", "refresh");
                    sendMessage(JSONObject.toJSONString(result));
                    return result;
                }
                // 已登录
                if (!user.equals(uid)) {
                    // 登录成成功
                    result.put("success", true);
                    result.put("stats", "ok");
                    return result;
                }
                // 等待二维码被扫
                try {
                    // 线程等待30秒
                    qrLogin.getLoginLatch(uid).await(10, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result.put("success", false);
                result.put("stats", "waiting");
                sendMessage(JSONObject.toJSONString(result));
                return result;

            } finally {
                // 移除登录请求
                qrLogin.removeLoginLatch(uid);
            }
        };
        return callable;
    }



    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        this.uid = uid;
        //在线数加1
        addOnlineCount();
        try {
            HashMap<String,String> hashMap = new HashMap<>(16);
            hashMap.put("msgType","login");
            hashMap.put("content","WebSocket 连接成功");
            String r = JSONObject.toJSONString(ResultUtils.success(hashMap));
            sendMessage(r);
            log.info("有新客户端加入，开始监听:" + uid + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        //断开连接情况下，更新主板占用情况为释放
        log.info("释放的uid为：" + uid);
        //这里写释放的时候要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端" + uid + "的信息:" + message);
        //群发消息
        for (WebSocketLoginServer item : webSocketSet) {
            try {
                item.sendMessage("服务端返回消息："+message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("uid") String uid) throws IOException {
        log.info("推送消息到窗口" + uid + "，推送内容:" + message);
        for (WebSocketLoginServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个uid的，为null则全部推送
                if (uid == null) {
                    item.sendMessage(message);
                } else if (item.uid.equals(uid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketLoginServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketLoginServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketLoginServer> getWebSocketSet() {
        return webSocketSet;
    }
}
