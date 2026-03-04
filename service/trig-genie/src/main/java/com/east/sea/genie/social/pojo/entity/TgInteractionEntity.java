package com.east.sea.genie.social.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 互动记录实体
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_interaction")
public class TgInteractionEntity extends BaseTablePropertyEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 目标ID（攻略/游记/评论）
     */
    private Long targetId;

    /**
     * 目标类型（1:攻略 2:游记 3:评论）
     */
    private Integer targetType;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 动作（1:点赞 2:收藏 3:关注）
     */
    private Integer action;
}
