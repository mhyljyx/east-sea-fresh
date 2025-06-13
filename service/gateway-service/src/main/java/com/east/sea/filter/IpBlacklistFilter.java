package com.east.sea.filter;

import cn.hutool.core.collection.CollUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;

@Component
public class IpBlacklistFilter implements GlobalFilter, Ordered {

    private static final Set<String> BLACKLIST = CollUtil.newHashSet("192.168.1.10");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("X-Forwarded-For"), "unknown");
        if (BLACKLIST.contains(ip)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
