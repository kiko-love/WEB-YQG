package com.yqg.utils;

import com.alibaba.fastjson.JSONObject;
import com.yqg.R.ResultEnum;
import com.yqg.websocket.WebSocketLoginServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author KIKO
 */
@Slf4j
@Component
public class DelayQrExpire {
    @Autowired
    private RedisUtils redisUtils;
    public void delayQrExpire(String key,String uid,Long delay) throws InterruptedException {
        //此处使用了异步线程池实现延时任务
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2);
        //只延时执行一次异步任务 参数：1、任务体 2、任务执行间隔 3、间隔时间单位
        service.schedule(() -> {
            redisUtils.del(key);
            log.info("二维码模拟过期： " + new Date());
            ConcurrentHashMap<Object, Object> data = new ConcurrentHashMap<>(16);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", ResultEnum.QR_EXPIRE.getCode());
            jsonObject.put("msg",ResultEnum.QR_EXPIRE.getMsg());
            data.put("msg","二维码已经过期");
            jsonObject.put("data",data);
            service.shutdown();
            try {
                WebSocketLoginServer.sendInfo(jsonObject.toJSONString(), uid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, delay, TimeUnit.SECONDS);
    }
}
