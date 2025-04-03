package com.east.sea.handler;

import com.alibaba.fastjson2.JSON;
import com.east.sea.manager.WebSocketManager;
import com.east.sea.pojo.struct.WSBarrageStruct;
import com.east.sea.pojo.struct.WSChatStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.socket.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class ChatWebSocketHandler extends WSHandler implements WebSocketHandler  {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        HandshakeInfo handshakeInfo = session.getHandshakeInfo();
        Map<String, String> queryMap = getQueryMap(handshakeInfo.getUri().getQuery());
        WSChatStruct wsChatStruct = JSON.parseObject(JSON.toJSONString(queryMap), WSChatStruct.class);
        //todo 需要改为token校验
        if (true) {
            // 处理接收的消息
            Mono<Void> input = session.receive()
                    .doOnNext(msg -> {
                        System.out.println("私聊消息：" + msg);
                        this.messageHandle(session, msg);
                    })
                    .doOnError(throwable -> log.error(wsChatStruct.getId() + ":Chat webSocket发生异常：" + throwable))
                    .doOnComplete(() -> log.info(wsChatStruct.getId() + ":Chat webSocket连接结束"))
                    .then();
            // 处理发送的消息
            Flux<WebSocketMessage> output = Flux.create(sink -> WebSocketManager.addChatUser(wsChatStruct.getId(), session, sink));
            return Mono.zip(input, session.send(output)).then();
        } else {
            return session.close(new CloseStatus(1016, "未通过校验,webSocket连接关闭"));
        }
    }

    @SuppressWarnings(value = "unused")
    private void messageHandle(WebSocketSession session, WebSocketMessage message) {
        // 接收客户端请求的处理回调
        switch (message.getType()) {
            case TEXT:
//                WebSocketManager.sendChatMessage(wsChatStruct.getId(), session.textMessage(msg)); // 发送私聊消息
            case BINARY:
            case PONG:
            case PING:
                break;
            default:
        }
    }

}
