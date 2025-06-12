DROP TABLE IF EXISTS oauth_client_details;
-- OAuth2客户端信息存储表（核心认证表）
CREATE TABLE oauth_client_details (
                                      client_id VARCHAR(256) PRIMARY KEY COMMENT '客户端唯一标识',
                                      client_name VARCHAR(256) NOT NULL COMMENT '客户端名称',
                                      client_secret VARCHAR(256) NOT NULL COMMENT '客户端密钥(需加密存储)',
                                      resource_ids VARCHAR(256) COMMENT '客户端可访问的资源ID集合,逗号分隔',
                                      scope VARCHAR(256) COMMENT '权限范围(如read,write)',
                                      authorized_grant_types VARCHAR(256) COMMENT '支持的授权模式(password,authorization_code等)',
                                      web_server_redirect_uri VARCHAR(256) COMMENT '授权码模式重定向URI',
                                      authorities VARCHAR(256) COMMENT '客户端拥有的权限,逗号分隔',
                                      access_token_validity INTEGER COMMENT '访问令牌有效期(秒)',
                                      refresh_token_validity INTEGER COMMENT '刷新令牌有效期(秒)',
                                      additional_information VARCHAR(4096) COMMENT '扩展信息(JSON格式)',
                                      autoapprove VARCHAR(256) COMMENT '自动授权的scope(如true或scope列表)',
                                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      is_del TINYINT DEFAULT 0 COMMENT '是否逻辑删除（0 否，1 是）'
) COMMENT 'OAuth2客户端注册信息表';

-- 为关键字段添加注释(MySQL语法)
ALTER TABLE oauth_client_details
    MODIFY COLUMN client_id VARCHAR(256) COMMENT '客户端唯一标识(如app_id)',
    MODIFY COLUMN client_secret VARCHAR(256) COMMENT 'BCrypt加密后的密钥';