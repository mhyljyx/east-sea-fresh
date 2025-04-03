package com.east.sea.config;

import com.east.sea.handler.BarrageWebSocketHandler;
import com.east.sea.handler.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping webSocketMapping(ChatWebSocketHandler chatHandler, BarrageWebSocketHandler barrageHandler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/chat", chatHandler);
        map.put("/ws/barrage", barrageHandler);
        return new SimpleUrlHandlerMapping(map, Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
