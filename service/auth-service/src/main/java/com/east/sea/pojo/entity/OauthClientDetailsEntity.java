package com.east.sea.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * OAuth2客户端信息存储表
 * @author tztang
 * @since 2025/06/11
 */
@Data
@TableName("oauth_client_details")
public class OauthClientDetailsEntity extends BaseTablePropertyEntity {

    /**
     * 客户端ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientName;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 资源ID列表，逗号分隔
     */
    private String resourceIds;
    /**
     * 授权范围，逗号分隔
     */
    private String scope;
    /**
     * 授权类型，逗号分隔
     */
    private String authorizedGrantTypes;
    /**
     * 重定向URI
     */
    private String webServerRedirectUri;
    /**
     * 授权的角色，逗号分隔
     */
    private String authorities;
    /**
     * 访问令牌有效期（秒）
     */
    private Integer accessTokenValidity;
    /**
     * 刷新令牌有效期（秒）
     */
    private Integer refreshTokenValidity;
    /**
     * 额外信息，JSON格式字符串
     */
    private String additionalInformation;
    /**
     * 是否自动批准，逗号分隔的授权范围列表
     */
    private String autoapprove;

}
