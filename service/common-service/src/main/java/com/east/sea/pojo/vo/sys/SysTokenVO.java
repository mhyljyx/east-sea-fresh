package com.east.sea.pojo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应
 *
 * @author tztang
 * @since 2026/02/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户登录响应")
public class SysTokenVO {

    @ApiModelProperty(value = "访问令牌")
    private String token;

}
