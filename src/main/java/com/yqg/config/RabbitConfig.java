package com.yqg.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KIKO
 */
@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "myExchange";
    public static final String QUEUE_NAME = "notificationQueue";
    public static final String ROUTING_KEY = "myRoutingKey";

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(myExchange()).with(ROUTING_KEY);
    }
}
