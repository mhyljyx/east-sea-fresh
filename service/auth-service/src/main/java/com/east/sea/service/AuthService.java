package com.east.sea.service;

import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.dto.sys.SysUserRegisterDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;

import java.util.Map;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     * @param loginDTO 登录参数
     * @return 令牌
     */
    SysTokenVO login(SysUserLoginDTO loginDTO);

    /**
     * 用户注册
     * @param registerDTO 注册参数
     */
    void register(SysUserRegisterDTO registerDTO);

    /**
     * 获取当前用户信息
     * @return 用户信息及权限
     */
    Map<String, Object> info();

}
