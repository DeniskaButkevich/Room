package com.dez.room;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import io.opentracing.Tracer;
import io.opentracing.contrib.spring.cloud.websocket.TracingChannelInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {WebsocketTest.WebSocketConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class WebsocketTest {

    @Configuration
    @EnableWebSocketMessageBroker
    @EnableAutoConfiguration
    public static class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

        @Bean
        public Tracer tracer() {
            return mock(Tracer.class);
        }

        @Override
        public void configureMessageBroker(MessageBrokerRegistry config) {
            config.enableSimpleBroker("/topic");
            config.setApplicationDestinationPrefixes("/app");
        }

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/websocket").withSockJS();
        }

    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        assertEquals(2, applicationContext.getBeansOfType(TracingChannelInterceptor.class).size());
    }
}
