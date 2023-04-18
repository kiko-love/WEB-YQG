package com.yqg.websocket;

import com.alibaba.fastjson.JSONObject;
import com.yqg.utils.RedisUtils;
import com.yqg.utils.ResultUtils;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author KIKO
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/api/websocket/msg/{uid}")
public class WebSocketMsgServer {
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final CopyOnWriteArraySet<WebSocketMsgServer> webSocketSet
            = new CopyOnWriteArraySet<WebSocketMsgServer>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收uid
     */
    private String uid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session ,@PathParam("uid") String uid) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        this.uid = uid;
        //在线数加1
        addOnlineCount();
        try {
            HashMap<String, String> hashMap = new HashMap<>(16);
            hashMap.put("msgType", "msg");
            hashMap.put("content", "WebSocket 连接成功");
            hashMap.put("uid", uid);
            String r = JSONObject.toJSONString(ResultUtils.success(hashMap));
            sendMessage(r);
            log.info("msgSocket频道有新客户端加入:" + uid + ",当前在线人数为:" + getOnlineCount());
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
        log.info("有一连接消息socket关闭！当前在线人数为" + getOnlineCount());

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
        for (WebSocketMsgServer item : webSocketSet) {
            try {
                item.sendMessage("服务端返回消息：" + message);
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
     * 群发自定义消息（也支持单个发送）
     */
    public static void sendInfo(String message, @PathParam("uid") String uid) throws IOException {
        log.info("推送消息到窗口" + uid + "，推送内容:" + message);
        for (WebSocketMsgServer item : webSocketSet) {
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
        WebSocketMsgServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketMsgServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketMsgServer> getWebSocketSet() {
        return webSocketSet;
    }
}
