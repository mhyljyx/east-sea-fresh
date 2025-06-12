package com.east.sea.pojo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询OauthClientDetails的参数类
 * @author tztang
 * @since 2025/06/11
 */
@Data
@ApiModel("查询OauthClientDetails的参数类")
public class OauthClientDetailsQueryParams extends PageParams {

    //查询参数 clientId||clientName
    @ApiModelProperty(value = "查询参数 clientId||clientName")
    private String queryInfo;

}
