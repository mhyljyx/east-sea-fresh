package com.east.sea.service.impl;

import com.east.sea.jwt.TokenUtil;
import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;
import com.east.sea.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import com.east.sea.enums.BaseCode;
import com.east.sea.exception.BusinessFrameException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    private String tokenSecret = "CSYZWECHAT";
    private long tokenTtl = 3600 * 1000 * 24L;

    @Override
    public SysTokenVO login(SysUserLoginDTO loginDTO) {
        try {
            // AuthenticationManager 会自动处理密码比对，不需要手动 encode
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getAccount(), loginDTO.getPassword());
            
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String token = TokenUtil.createToken(UUID.randomUUID().toString(), loginDTO.getAccount(), tokenTtl, tokenSecret);
            
            return new SysTokenVO(token);
        } catch (AuthenticationException e) {
            // 捕获认证异常，抛出自定义业务异常
            throw new BusinessFrameException(BaseCode.NAME_PASSWORD_ERROR);
        }
    }

}
