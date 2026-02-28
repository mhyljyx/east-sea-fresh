package com.east.sea.genie.strategy.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 目的地实体
 * 
 * 对应数据库表 tg_destination
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_destination")
public class TgDestinationEntity extends BaseTablePropertyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 类型（1:国家 2:省份 3:城市 4:景点）
     */
    private Integer type;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 简介
     */
    private String description;
}
