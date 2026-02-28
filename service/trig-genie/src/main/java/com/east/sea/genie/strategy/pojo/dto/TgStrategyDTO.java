package com.east.sea.genie.strategy.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.annotation.EditType;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 攻略信息DTO
 * 
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@ApiModel(value = "攻略信息参数", description = "攻略信息DTO")
public class TgStrategyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EditType({EditType.INSERT, EditType.UPDATE})
    @ApiModelProperty(value = "攻略id", example = "1")
    private Long id;

    @EditType({EditType.INSERT})
    @ApiModelProperty(value = "关联目的地ID", example = "1")
    private Long destId;

    @EditType({EditType.INSERT, EditType.UPDATE})
    @ApiModelProperty(value = "标题", example = "战斗")
    private String title;

    @EditType({EditType.INSERT, EditType.UPDATE})
    @ApiModelProperty(value = "内容", example = "欢迎来到卡莫纳")
    private String content;

    @EditType({EditType.INSERT, EditType.UPDATE})
    @ApiModelProperty(value = "分类（1:美食 2:景点 3:路线）", example = "1")
    private Integer category;

    @EditType({EditType.UPDATE})
    @ApiModelProperty(value = "状态（0:草稿 1:发布 2:下架）", example = "0")
    private Integer status;

}
