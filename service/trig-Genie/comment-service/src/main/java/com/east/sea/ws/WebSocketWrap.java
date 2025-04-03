package com.east.sea.ws;

import lombok.Data;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Data
public class WebSocketWrap {

    private final String id;
    private final WebSocketSession session;
    private final FluxSink<WebSocketMessage> sink;

    public WebSocketWrap(String id, WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        this.id = id;
        this.session = session;
        this.sink = sink;  // 保存 sink，以便后续推送消息
    }

    public void sendMessage(WebSocketMessage message) {
        if (sink != null) {
            sink.next(message);
        }
    }

}
