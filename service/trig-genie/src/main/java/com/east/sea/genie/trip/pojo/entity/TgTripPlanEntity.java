package com.east.sea.genie.trip.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 行程单实体
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_trip_plan")
public class TgTripPlanEntity extends BaseTablePropertyEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 行程标题
     */
    private String title;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 天数
     */
    private Integer dayCount;

    /**
     * 隐私（0:公开 1:私密）
     */
    private Integer privacy;
}
