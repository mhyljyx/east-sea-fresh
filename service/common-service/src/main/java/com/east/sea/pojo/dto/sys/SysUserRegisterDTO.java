package com.east.sea.pojo.dto.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册参数
 *
 * @author tztang
 * @since 2026/02/27
 */
@Data
@ApiModel(value = "用户注册参数")
public class SysUserRegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true, example = "admin")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", example = "admin@example.com")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "昵称", example = "管理员")
    private String userName;

}
