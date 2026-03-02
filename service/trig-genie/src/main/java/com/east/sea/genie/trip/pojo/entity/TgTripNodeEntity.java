package com.east.sea.genie.trip.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

/**
 * 行程节点实体
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_trip_node")
public class TgTripNodeEntity extends BaseTablePropertyEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 行程ID
     */
    private Long planId;

    /**
     * 第几天（1, 2...）
     */
    private Integer dayIndex;

    /**
     * 当日排序
     */
    private Integer sortOrder;

    /**
     * 关联地点ID
     */
    private Long destId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 建议开始时间
     */
    private LocalTime startTime;
}
