package com.east.sea.pojo.dto.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数
 *
 * @author tztang
 * @since 2026/02/27
 */
@Data
@ApiModel(value = "用户登录参数")
public class SysUserLoginDTO {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true, example = "admin")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

}
