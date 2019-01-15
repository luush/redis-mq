package org.lszjaf.dagger.redismqconsumer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
@RestController
public class Start {
    @Autowired
    RedisMessageListenerContainer redisMessageListenerContainer;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Consumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @PostConstruct
    public void st() {
        redisMessageListenerContainer.start();
    }
}
