package com.east.sea.pojo.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OauthClientDetailsParams {

    @ApiModelProperty(name = "clientId", value = "客户端ID", example = "更新时必传")
    private String clientId;

    @NotBlank(message = "客户端名称不能为空")
    @ApiModelProperty(name = "clientName", value = "客户端名称", required = true)
    private String clientName;

    @NotBlank(message = "客户端密钥不能为空")
    @ApiModelProperty(name = "clientSecret", value = "客户端密钥", required = true)
    private String clientSecret;

    @ApiModelProperty(name = "resourceIds", value = "资源ID列表，逗号分隔")
    private String resourceIds;

    @ApiModelProperty(name = "scope", value = "授权范围，逗号分隔")
    private String scope;

    @ApiModelProperty(name = "authorizedGrantTypes", value = "授权类型，逗号分隔")
    private String authorizedGrantTypes;

    @ApiModelProperty(name = "webServerRedirectUri", value = "重定向URI")
    private String webServerRedirectUri;

    @ApiModelProperty(name = "authorities", value = "授权的角色，逗号分隔")
    private String authorities;

    @ApiModelProperty(name = "accessTokenValidity", value = "访问令牌有效期（秒）")
    private Integer accessTokenValidity;

    @ApiModelProperty(name = "refreshTokenValidity", value = "刷新令牌有效期（秒）")
    private Integer refreshTokenValidity;

    @ApiModelProperty(name = "additionalInformation", value = "额外信息，JSON格式字符串")
    private String additionalInformation;

    @ApiModelProperty(name = "autoapprove", value = "是否自动批准，逗号分隔的授权范围列表")
    private String autoapprove;

}
