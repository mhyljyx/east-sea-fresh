package com.east.sea.genie.trip.pojo.dto;

import com.east.sea.annotation.EditType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * 行程节点创建/更新DTO
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@ApiModel(value = "行程节点参数", description = "行程节点DTO")
public class TgTripNodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "行程节点id不能为空")
    @EditType({EditType.UPDATE})
    @ApiModelProperty(value = "行程节点id", example = "1")
    private Long id;

    @NotNull(message = "行程第几天不能为空")
    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "第几天（1, 2...）", required = true, example = "1")
    private Integer dayIndex;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "行程排序", example = "1")
    private Integer sortOrder;

    @NotNull(message = "关联地点ID不能为空")
    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "关联地点ID", required = true, example = "101")
    private Long destId;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "备注", example = "记得带相机")
    private String memo;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "建议开始时间", example = "09:00:00")
    private LocalTime startTime;
}
