package com.east.sea.genie.trip.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 行程详情VO
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@ApiModel(value = "行程详情", description = "行程详情VO")
public class TgTripPlanVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "用户ID", example = "10086")
    private Long userId;

    @ApiModelProperty(value = "行程标题", example = "杭州三日游")
    private String title;

    @ApiModelProperty(value = "开始日期", example = "2026-05-01")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期", example = "2026-05-03")
    private LocalDate endDate;

    @ApiModelProperty(value = "天数", example = "3")
    private Integer dayCount;

    @ApiModelProperty(value = "隐私（0:公开 1:私密）", example = "0")
    private Integer privacy;
    
    @ApiModelProperty(value = "创建时间", example = "2026-02-28 12:00:00")
    private Date createTime;
}
