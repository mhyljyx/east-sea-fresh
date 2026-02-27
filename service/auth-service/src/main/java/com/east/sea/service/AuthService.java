package com.east.sea.service;

import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.vo.sys.SysTokenVO;

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

}
