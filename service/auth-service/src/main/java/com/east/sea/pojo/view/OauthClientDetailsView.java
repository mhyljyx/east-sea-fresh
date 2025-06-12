package com.east.sea.pojo.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OAuth2客户端信息
 * @author tztang
 * @since 2025/06/11
 */
@Data
@ApiModel("OAuth2客户端信息视图")
public class OauthClientDetailsView {

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥")
    private String clientName;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "资源ID列表，逗号分隔")
    private String resourceIds;

    @ApiModelProperty(value = "授权范围，逗号分隔")
    private String scope;

    @ApiModelProperty(value = "授权类型，逗号分隔")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向URI")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "授权的角色，逗号分隔")
    private String authorities;

    @ApiModelProperty(value = "访问令牌有效期（秒）")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新令牌有效期（秒）")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "额外信息，JSON格式字符串")
    private String additionalInformation;

    @ApiModelProperty(value = "是否自动批准，逗号分隔的授权范围列表")
    private String autoapprove;

}
