package com.east.sea.pojo.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户表
 *
 * @author tztang
 * @since 2026/02/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUserEntity extends BaseTablePropertyEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型 0:普通用户 1:管理员
     */
    private Integer userType;

    /**
     * 状态 0:正常 1:禁用
     */
    private Integer status;

}
