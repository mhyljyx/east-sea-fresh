package com.east.sea.controller;

import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.dto.sys.SysUserRegisterDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;
import com.east.sea.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * 认证控制器
 *
 * 负责用户登录、获取信息等认证相关接口
 * @author tztang
 * @since 2026/02/27
 */
@RestController
@RequestMapping("/auth/")
@Api(tags = "认证模块")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数
     * @return 令牌信息
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public ApiResponse<SysTokenVO> login(@RequestBody @Valid SysUserLoginDTO loginDTO) {
        return ApiResponse.ok(authService.login(loginDTO));
    }

    /**
     * 用户注册
     *
     * @param registerDTO 用户注册
     */
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ApiResponse<Void> register(@RequestBody @Valid SysUserRegisterDTO registerDTO) {
        authService.register(registerDTO);
        return ApiResponse.ok();
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息及权限
     */
    @GetMapping("info")
    @ApiOperation(value = "获取当前用户信息")
    public ApiResponse<Map<String, Object>> info() {
        return ApiResponse.ok(authService.info());
    }

}
