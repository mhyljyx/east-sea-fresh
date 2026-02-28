package com.east.sea.pojo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户响应
 *
 * @author tztang
 * @since 2026/02/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "系统用户响应")
public class SysUserVO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户类型 0:普通用户 1:管理员")
    private Integer userType;

    @ApiModelProperty(value = "状态 0:正常 1:禁用")
    private Integer status;

}
