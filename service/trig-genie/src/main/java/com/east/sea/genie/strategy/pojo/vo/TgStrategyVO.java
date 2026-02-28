package com.east.sea.genie.strategy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 攻略视图对象
 *
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@ApiModel(value = "攻略详情", description = "攻略详情VO")
public class TgStrategyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "关联目的地ID", example = "101")
    private Long destId;

    @ApiModelProperty(value = "作者ID", example = "10086")
    private Long userId;

    @ApiModelProperty(value = "标题", example = "杭州三日游攻略")
    private String title;

    @ApiModelProperty(value = "内容", example = "第一天...")
    private String content;

    @ApiModelProperty(value = "分类（1:美食 2:景点 3:路线）", example = "3")
    private Integer category;

    @ApiModelProperty(value = "状态（0:草稿 1:发布 2:下架）", example = "1")
    private Integer status;

    @ApiModelProperty(value = "浏览量", example = "999")
    private Integer viewCount;
    
    @ApiModelProperty(value = "创建时间", example = "2026-02-27 12:00:00")
    private Date createTime;
}
