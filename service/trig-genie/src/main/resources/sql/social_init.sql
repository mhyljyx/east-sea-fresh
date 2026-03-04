-- 游记/动态表
CREATE TABLE `tg_travel_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '发布者ID',
  `content` varchar(2000) DEFAULT NULL COMMENT '文本内容',
  `media_urls` json DEFAULT NULL COMMENT '图片/视频地址列表',
  `location` varchar(255) DEFAULT NULL COMMENT '定位信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游记/动态表';

-- 互动记录表
CREATE TABLE `tg_interaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID（攻略/游记/评论）',
  `target_type` tinyint(4) NOT NULL COMMENT '目标类型（1:攻略 2:游记 3:评论）',
  `user_id` bigint(20) NOT NULL COMMENT '操作用户ID',
  `action` tinyint(4) NOT NULL COMMENT '动作（1:点赞 2:收藏 3:关注）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target_action` (`user_id`,`target_id`,`target_type`,`action`),
  KEY `idx_target` (`target_id`,`target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='互动记录表';
