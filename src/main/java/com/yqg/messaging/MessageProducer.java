package com.yqg.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author KIKO
 */
@Component
public class MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void send(String message) {
        rabbitTemplate.convertAndSend("myExchange", "myRoutingKey", message);
    }
}
