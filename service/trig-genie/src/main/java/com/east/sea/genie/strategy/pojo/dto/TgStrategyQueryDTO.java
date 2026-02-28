package com.east.sea.genie.strategy.pojo.dto;

import com.east.sea.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 攻略查询DTO
 *
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@ApiModel(value = "攻略查询参数", description = "攻略查询DTO")
public class TgStrategyQueryDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词", example = "西湖")
    private String keyword;

    @ApiModelProperty(value = "目的地ID", example = "101")
    private Long destId;

}
