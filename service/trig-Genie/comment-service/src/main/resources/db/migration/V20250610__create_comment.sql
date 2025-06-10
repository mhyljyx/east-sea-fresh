DROP TABLE IF EXISTS c_comment;
CREATE TABLE c_comment (
     id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
     content TEXT NOT NULL COMMENT '评论内容',
     user_id BIGINT NOT NULL COMMENT '发表评论的用户ID',
     target_id BIGINT NOT NULL COMMENT '评论目标ID，例如文章ID、视频ID等',
     target_type VARCHAR(50) COMMENT '评论目标类型（如 article、video）',
     parent_id BIGINT DEFAULT 0 COMMENT '父评论ID，0表示一级评论',
     root_id BIGINT DEFAULT 0 COMMENT '顶层评论ID，用于快速定位评论主楼',
     like_count INT DEFAULT 0 COMMENT '评论点赞数量',
     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     is_del TINYINT DEFAULT 0 COMMENT '是否逻辑删除（0 否，1 是）'
) COMMENT='评论表，用于存储文章、视频等内容的用户评论';
