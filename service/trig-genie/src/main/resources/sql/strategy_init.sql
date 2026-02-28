
-- 目的地表
CREATE TABLE `tg_destination` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `type` tinyint(4) NOT NULL COMMENT '类型（1:国家 2:省份 3:城市 4:景点）',
  `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图',
  `description` text COMMENT '简介',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='目的地表';

-- 攻略表
CREATE TABLE `tg_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dest_id` bigint(20) NOT NULL COMMENT '关联目的地ID',
  `user_id` bigint(20) NOT NULL COMMENT '作者ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `category` tinyint(4) NOT NULL COMMENT '分类（1:美食 2:景点 3:路线）',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0:草稿 1:发布 2:下架）',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_dest_id` (`dest_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='攻略表';
