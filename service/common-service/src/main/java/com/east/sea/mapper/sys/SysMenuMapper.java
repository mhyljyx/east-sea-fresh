package com.east.sea.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.east.sea.pojo.entity.sys.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    List<String> selectPermsByUserId(@Param("userId") Long userId);

}
