package com.yqg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.messaging.MessageProducer;
import com.yqg.service.IMessageService;
import com.yqg.utils.RedisUtils;
import com.yqg.vo.BroadcastMsg;
import com.yqg.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.yqg.R.NomalEnum.MSG_KEY;

/**
 * @author KIKO
 */
@Service
@Slf4j
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 广播消息
     *
     * @param message
     * @return
     */
    @Override
    public String broadcastMessage(BroadcastMsg message) {
        messageProducer.send(message.toString());
        return Result.success(message);

    }

    @Override
    public void sendMessage(String uid, Message message) {

    }

    public String getMsgUid(String userId) {
        long expire = 5 * 60L;
        String prefix = MSG_KEY + userId;
        String uid = IdUtil.simpleUUID();
        HashMap<String,String> hashMap = new HashMap<>(16);
        hashMap.put("uid",uid);
        redisUtils.set(prefix, uid, expire);
        return Result.success(hashMap);
    }
}
