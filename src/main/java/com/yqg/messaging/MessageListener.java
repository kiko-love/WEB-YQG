package com.yqg.messaging;

import com.yqg.websocket.WebSocketMsgServer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author KIKO
 */
@Component
@RabbitListener(queues = "notificationQueue")
public class MessageListener {

    /**
     * 接收消息
     * @param message 消息体
     * @throws IOException
     */
    @RabbitHandler
    public void processMessage(String message) throws IOException {
        WebSocketMsgServer.sendInfo(message,null);
        System.out.println("Received message: " + message);

    }
}
