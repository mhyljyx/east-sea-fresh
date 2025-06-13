package com.east.sea.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Oauth客户端信息删除参数
 * @author tztang
 * @since 2025/06/13
 */
@Data
public class OauthClientDetailsDelParams {

    @NotEmpty(message = "Oauth客户端信息ID集合[ids]不能为空")
    @ApiModelProperty(value = "Oauth客户端信息ID集合", required = true)
    private Set<String> ids;

}
