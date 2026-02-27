package com.east.sea.service;

import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;
import com.east.sea.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testLogin() {
        // Arrange
        SysUserLoginDTO loginDTO = new SysUserLoginDTO();
        loginDTO.setAccount("admin");
        loginDTO.setPassword("123456");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act
        SysTokenVO tokenVO = authService.login(loginDTO);

        // Assert
        Assertions.assertNotNull(tokenVO);
        Assertions.assertNotNull(tokenVO.getToken());
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }
}
