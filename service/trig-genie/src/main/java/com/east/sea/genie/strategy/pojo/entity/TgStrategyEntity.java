package com.east.sea.genie.strategy.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 攻略实体
 * 
 * 对应数据库表 tg_strategy
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_strategy")
public class TgStrategyEntity extends BaseTablePropertyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联目的地ID
     */
    private Long destId;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 分类（1:美食 2:景点 3:路线）
     */
    private Integer category;

    /**
     * 状态（0:草稿 1:发布 2:下架）
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer viewCount;
}
