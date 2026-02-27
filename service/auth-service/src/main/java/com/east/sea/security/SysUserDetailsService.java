package com.east.sea.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.east.sea.mapper.sys.SysMenuMapper;
import com.east.sea.mapper.sys.SysUserMapper;
import com.east.sea.pojo.entity.sys.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getAccount, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<String> perms = sysMenuMapper.selectPermsByUserId(user.getId());
        return new SysUserDetails(user, perms);
    }

}
