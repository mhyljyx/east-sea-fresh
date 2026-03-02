-- 行程单表
CREATE TABLE `tg_trip_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(200) NOT NULL COMMENT '行程标题',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `day_count` int(11) DEFAULT '0' COMMENT '天数',
  `privacy` tinyint(4) DEFAULT '0' COMMENT '隐私（0:公开 1:私密）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行程单表';

-- 行程节点表
CREATE TABLE `tg_trip_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_id` bigint(20) NOT NULL COMMENT '行程ID',
  `day_index` int(11) NOT NULL COMMENT '第几天（1, 2...）',
  `sort_order` int(11) DEFAULT '0' COMMENT '当日排序',
  `dest_id` bigint(20) NOT NULL COMMENT '关联地点ID',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `start_time` time DEFAULT NULL COMMENT '建议开始时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` char(1) DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_dest_id` (`dest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行程节点表';
