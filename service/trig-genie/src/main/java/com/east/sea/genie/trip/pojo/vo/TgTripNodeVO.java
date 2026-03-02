package com.east.sea.genie.trip.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 行程节点详情VO
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@ApiModel(value = "行程节点详情", description = "行程节点详情VO")
public class TgTripNodeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "行程ID", example = "10")
    private Long planId;

    @ApiModelProperty(value = "第几天", example = "1")
    private Integer dayIndex;

    @ApiModelProperty(value = "当日排序", example = "1")
    private Integer sortOrder;

    @ApiModelProperty(value = "关联地点ID", example = "101")
    private Long destId;
    
    @ApiModelProperty(value = "地点名称（需要关联查询）", example = "西湖")
    private String destName;

    @ApiModelProperty(value = "备注", example = "记得带相机")
    private String memo;

    @ApiModelProperty(value = "建议开始时间", example = "09:00:00")
    private LocalTime startTime;
}
