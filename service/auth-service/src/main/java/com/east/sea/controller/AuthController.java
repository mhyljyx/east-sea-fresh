package com.east.sea.controller;

import cn.hutool.core.collection.CollUtil;
import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;
import com.east.sea.pojo.vo.sys.SysUserVO;
import com.east.sea.security.SysUserDetails;
import com.east.sea.service.AuthService;
import com.east.sea.util.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        SysTokenVO tokenVO = authService.login(loginDTO);
        return ApiResponse.ok(tokenVO);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息及权限
     */
    @GetMapping("info")
    @ApiOperation(value = "获取当前用户信息")
    public ApiResponse<Map<String, Object>> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
        
        Map<String, Object> data = new HashMap<>();
        SysUserVO sysUserVO = new SysUserVO();
        CopyUtil.copyProperties(userDetails.getSysUser(), sysUserVO);
        data.put("user", sysUserVO);
        data.put("permissions", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        
        return ApiResponse.ok(data);
    }

}

