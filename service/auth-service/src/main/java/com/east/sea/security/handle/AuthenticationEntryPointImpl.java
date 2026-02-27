package com.east.sea.security.handle;

import com.alibaba.fastjson2.JSON;
import com.east.sea.common.ApiResponse;
import com.east.sea.enums.BaseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author tztang
 * @since 2026/02/27
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置响应状态码为 200，内容通过 ApiResponse 返回
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        
        // 返回未授权错误
        ApiResponse<String> result = ApiResponse.error(BaseCode.UNAUTHORIZED);
        response.getWriter().print(JSON.toJSONString(result));
    }
}
