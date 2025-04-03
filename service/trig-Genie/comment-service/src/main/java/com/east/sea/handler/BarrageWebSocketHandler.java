package com.east.sea.handler;

import com.alibaba.fastjson2.JSON;
import com.east.sea.manager.WebSocketManager;
import com.east.sea.pojo.struct.WSBarrageStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class BarrageWebSocketHandler extends WSHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        HandshakeInfo handshakeInfo = session.getHandshakeInfo();
        Map<String, String> queryMap = getQueryMap(handshakeInfo.getUri().getQuery());
        WSBarrageStruct wsBarrageStruct = JSON.parseObject(JSON.toJSONString(queryMap), WSBarrageStruct.class);
        // 处理接收的消息
        Mono<Void> input = session.receive()
                .doOnNext(msg -> {
                    System.out.println("弹幕：" + msg);
                    this.messageHandle(session, msg);
                })
                .log()
                .doOnError(throwable -> log.error(wsBarrageStruct.getId() + ":Barrage webSocket发生异常：" + throwable))
                .doOnComplete(() -> log.info(wsBarrageStruct.getId() + ":Barrage webSocket连接结束"))
                .then();
        // 处理发送的消息
        Flux<WebSocketMessage> output = Flux.create(sink -> WebSocketManager.addBarrageUser(wsBarrageStruct.getId(), session, sink));
        return Mono.zip(input, session.send(output)).then();
    }

    @SuppressWarnings(value = "unused")
    private void messageHandle(WebSocketSession session, WebSocketMessage message) {
        // 接收客户端请求的处理回调
        switch (message.getType()) {
            case TEXT:
//                WebSocketManager.sendBarrage(session.textMessage(msg)); // 广播弹幕
            case BINARY:
            case PONG:
            case PING:
                break;
            default:
        }
    }

}
