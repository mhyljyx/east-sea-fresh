package com.east.sea.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.east.sea.enums.BaseCode;
import com.east.sea.exception.BusinessFrameException;
import com.east.sea.jwt.TokenUtil;
import com.east.sea.mapper.sys.SysRoleMapper;
import com.east.sea.mapper.sys.SysUserMapper;
import com.east.sea.mapper.sys.SysUserRoleMapper;
import com.east.sea.pojo.dto.sys.SysUserLoginDTO;
import com.east.sea.pojo.dto.sys.SysUserRegisterDTO;
import com.east.sea.pojo.entity.sys.SysRoleEntity;
import com.east.sea.pojo.entity.sys.SysUserEntity;
import com.east.sea.pojo.entity.sys.SysUserRoleEntity;
import com.east.sea.pojo.vo.sys.SysTokenVO;
import com.east.sea.pojo.vo.sys.SysUserVO;
import com.east.sea.security.SysUserDetails;
import com.east.sea.service.AuthService;
import com.east.sea.util.CopyUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(SysUserRegisterDTO registerDTO) {
        // 1. 校验账号是否已存在
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getAccount, registerDTO.getAccount());
        if (sysUserMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessFrameException(BaseCode.PARAMETER_ERROR.getCode(), "账号已存在");
        }

        // 2. 校验邮箱
        if (StrUtil.isNotBlank(registerDTO.getEmail())) {
            queryWrapper.clear();
            queryWrapper.eq(SysUserEntity::getEmail, registerDTO.getEmail());
            if (sysUserMapper.selectCount(queryWrapper) > 0) {
                throw new BusinessFrameException(BaseCode.PARAMETER_ERROR.getCode(), "邮箱已被注册");
            }
        }

        // 3. 校验手机号
        if (StrUtil.isNotBlank(registerDTO.getPhone())) {
            queryWrapper.clear();
            queryWrapper.eq(SysUserEntity::getPhone, registerDTO.getPhone());
            if (sysUserMapper.selectCount(queryWrapper) > 0) {
                throw new BusinessFrameException(BaseCode.PARAMETER_ERROR.getCode(), "手机号已被注册");
            }
        }

        // 4. 创建用户
        SysUserEntity user = new SysUserEntity();
        user.setAccount(registerDTO.getAccount());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setUserName(StrUtil.isNotBlank(registerDTO.getUserName()) ? registerDTO.getUserName() : registerDTO.getAccount());
        user.setNickName(user.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setStatus(0); // 正常
        user.setUserType(0); // 普通用户
        
        sysUserMapper.insert(user);

        // 5. 分配默认角色 (普通用户 ROLE_USER)
        LambdaQueryWrapper<SysRoleEntity> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysRoleEntity::getRoleCode, "ROLE_USER");
        SysRoleEntity role = sysRoleMapper.selectOne(roleWrapper);
        
        if (role != null) {
            SysUserRoleEntity userRole = new SysUserRoleEntity();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            sysUserRoleMapper.insert(userRole);
        }
    }

    @Override
    public Map<String, Object> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        SysUserVO sysUserVO = new SysUserVO();
        CopyUtil.copyProperties(userDetails.getSysUser(), sysUserVO);
        data.put("user", sysUserVO);
        data.put("permissions", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return data;
    }

}
