package com.east.sea.pojo.dto.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "菜单清单节点")
public class MenuNodeDTO implements Serializable {

    @NotBlank(message = "key不能为空")
    @ApiModelProperty(value = "唯一键，用于父子关联与F级绑定，例如 system:user", required = true)
    private String key;

    @ApiModelProperty(value = "父级key，为空则作为根")
    private String parentKey;

    @NotBlank(message = "menuName不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String menuName;

    @NotBlank(message = "type不能为空")
    @ApiModelProperty(value = "类型：M目录/C页面", required = true, allowableValues = "M,C")
    private String type;

    @ApiModelProperty(value = "路由路径（M建议为/模块，C为相对子路由）")
    private String path;

    @ApiModelProperty(value = "组件路径（前端组件名）")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序，默认0")
    private Integer sort;

    @ApiModelProperty(value = "权限前缀，供F级权限归属匹配，如 system:user")
    private String permPrefix;
}

