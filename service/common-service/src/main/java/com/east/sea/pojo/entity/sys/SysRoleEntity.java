package com.east.sea.pojo.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRoleEntity extends BaseTablePropertyEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String description;

}
