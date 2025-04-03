package com.east.sea.manager;

import com.east.sea.ws.WebSocketWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketManager {

    public static final Map<String, WebSocketWrap> BARRAGE_USERS = new ConcurrentHashMap<>();
    public static final Map<String, WebSocketWrap> CHAT_USERS = new ConcurrentHashMap<>();

    public static void addBarrageUser(String id, WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        BARRAGE_USERS.put(id, new WebSocketWrap(id, session, sink));
    }

    public static void removeBarrageUser(String id) {
        BARRAGE_USERS.remove(id);
    }

    public static void addChatUser(String id, WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        CHAT_USERS.put(id, new WebSocketWrap(id, session, sink));
    }

    public static void removeChatUser(String id) {
        CHAT_USERS.remove(id);
    }

    public static void sendBarrage(WebSocketMessage message) {
        BARRAGE_USERS.values().forEach(webSocket -> webSocket.sendMessage(message));
    }

    public static void sendChatMessage(String targetUserId, WebSocketMessage message) {
        WebSocketWrap targetWebSocket = CHAT_USERS.get(targetUserId);
        if (targetWebSocket != null) {
            targetWebSocket.sendMessage(message);
        }
    }

    /**
     * 清理不可用的SESSION
     * @since jdk11
     * @date 2022/4/13
     * @return void
     */
    @SuppressWarnings("AlibabaThreadPoolCreation")
    public static void purge() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            new ArrayList<>(BARRAGE_USERS.values()).forEach(wrap -> {
                if (!wrap.getSession().isOpen()) {
                    log.warn(String.format("用户ID: [%s] 的session: [%s] 已经关闭，将被清理", wrap.getId(), wrap.getSession().getId()));
                    BARRAGE_USERS.remove(wrap.getId());
                    wrap.getSession().close();
                }
            });
        }, 30, 30, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            new ArrayList<>(CHAT_USERS.values()).forEach(wrap -> {
                if (!wrap.getSession().isOpen()) {
                    log.warn(String.format("用户ID: [%s] 的session: [%s] 已经关闭，将被清理", wrap.getId(), wrap.getSession().getId()));
                    CHAT_USERS.remove(wrap.getId());
                    wrap.getSession().close();
                }
            });
        }, 30, 30, TimeUnit.SECONDS);
    }

}
