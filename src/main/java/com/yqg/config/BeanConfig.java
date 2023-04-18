package com.yqg.config;

import com.yqg.vo.QrLogin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author KIKO
 */
@Configuration
public class BeanConfig {
    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅登录消息
        container.addMessageListener(listenerAdapter, new PatternTopic(QrLogin.TOPIC_NAME));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(QrLogin qrLogin) {
        // 方法名
        String methodName = "receiveLogin";
        return new MessageListenerAdapter(qrLogin, methodName);
    }

    @Bean
    public QrLogin qrLogin() {
        return new QrLogin();
    }

}
