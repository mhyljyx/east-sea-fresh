package com.east.sea.genie.social.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 互动操作DTO
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@Data
@ApiModel(value = "互动操作参数", description = "互动操作DTO")
public class TgInteractionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "目标ID不能为空")
    @ApiModelProperty(value = "目标ID", required = true, example = "1001")
    private Long targetId;

    @NotNull(message = "目标类型不能为空")
    @ApiModelProperty(value = "目标类型（1:攻略 2:游记 3:评论）", required = true, example = "2")
    private Integer targetType;

    @NotNull(message = "动作类型不能为空")
    @ApiModelProperty(value = "动作（1:点赞 2:收藏 3:关注）", required = true, example = "1")
    private Integer action;
}
