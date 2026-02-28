package com.east.sea.genie.strategy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 目的地视图对象
 *
 * @author  tztang
 * @since 2026-02-27
 */
@Data
@ApiModel(value = "目的地详情", description = "目的地详情VO")
public class TgDestinationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "名称", example = "杭州")
    private String name;

    @ApiModelProperty(value = "父级ID", example = "100")
    private Long parentId;

    @ApiModelProperty(value = "类型（1:国家 2:省份 3:城市 4:景点）", example = "3")
    private Integer type;

    @ApiModelProperty(value = "封面图", example = "http://xxx.com/img.jpg")
    private String coverUrl;

    @ApiModelProperty(value = "简介", example = "上有天堂，下有苏杭")
    private String description;
    
    @ApiModelProperty(value = "创建时间", example = "2026-02-27 12:00:00")
    private Date createTime;
}
