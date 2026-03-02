package com.east.sea.genie.trip.pojo.dto;

import com.east.sea.annotation.EditType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 行程创建/更新DTO
 *
 * @author TraeAI
 * @since 2026-02-28
 */
@Data
@ApiModel(value = "行程创建/更新参数", description = "行程创建/更新DTO")
public class TgTripPlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EditType(EditType.UPDATE)
    private Long id;

    @NotBlank(message = "行程标题不能为空")
    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "行程标题", required = true, example = "杭州三日游")
    private String title;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "开始日期", example = "2026-05-01")
    private LocalDate startDate;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "结束日期", example = "2026-05-03")
    private LocalDate endDate;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "天数", example = "3")
    private Integer dayCount;

    @EditType({EditType.UPDATE, EditType.INSERT})
    @ApiModelProperty(value = "隐私（0:公开 1:私密）", example = "0")
    private Integer privacy;
}
