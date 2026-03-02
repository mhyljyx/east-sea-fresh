package com.east.sea.genie.trip.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 批量更新行程节点DTO
 *
 * @author TraeAI
 * @since 2026-03-02
 */
@Data
@ApiModel(value = "批量更新行程节点", description = "批量更新行程节点DTO")
public class TgTripNodeUpdateBatchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行程id", example = "1")
    private Long tripPlanId;

    @NotEmpty(message = "行程节点列表不能为空")
    @ApiModelProperty(value = "行程节点列表", example = "[{}, ...]")
    private List<TgTripNodeDTO> tripNodeDTOList;

}
