package com.yqg.service;

import com.yqg.vo.BroadcastMsg;
import com.yqg.vo.Message;

/**
 * @author KIKO
 */
public interface IMessageService {
    /**
     * 广播消息
     *
     * @param message
     * @return
     */
    String broadcastMessage(BroadcastMsg message);
    /**
     * 发送消息
     *
     * @param toId
     * @param message
     */
    void sendMessage(String toId,Message message);
}
