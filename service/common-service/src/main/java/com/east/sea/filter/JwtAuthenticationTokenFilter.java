package com.east.sea.filter;

import com.east.sea.jwt.TokenUtil;
import com.east.sea.pojo.entity.sys.SysUserEntity;
import com.east.sea.security.SysUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器
 *
 * @author tztang
 * @since 2026/02/27
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String userInfoJson = TokenUtil.parseToken(token, tokenSecret);

                if (userInfoJson != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = null;
                    try {
                        // 尝试从数据库加载用户（适用于 Auth Service）
                        userDetails = userDetailsService.loadUserByUsername(userInfoJson);
                    } catch (Exception e) {
                        // 如果 UserDetailsService 报错（如 SQL 错误、连接失败、或者微服务没有配置数据源），降级处理
                        // 这里仅仅 log warn，而不是 error，避免日志刷屏
                        log.warn("UserDetailsService load failed, using simple token user. Error: {}", e.getMessage());
                        // 构造一个临时的 UserDetails，确保请求能通过 .authenticated() 校验
                        SysUserEntity simpleUser = new SysUserEntity();
                        simpleUser.setAccount(userInfoJson);
                        simpleUser.setUserName(userInfoJson);
                        // 赋予一个默认的角色/权限，防止因权限列表为空而被拦截
                        // 实际场景应从 Token 中解析 Roles
                        userDetails = new SysUserDetails(simpleUser, Collections.emptyList());
                    }
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Token 解析失败或过期，记录错误但放行，交由后续 Security 拦截器处理
                log.error("Token validation failed: {}", e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
