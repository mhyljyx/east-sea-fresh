package com.east.sea.pojo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "权限项")
public class PermItemVO implements Serializable {

    @ApiModelProperty(value = "权限编码")
    private String perm;

    @ApiModelProperty(value = "HTTP方法")
    private String httpMethod;

    @ApiModelProperty(value = "完整路径")
    private String path;

    @ApiModelProperty(value = "来源类")
    private String sourceClass;

    @ApiModelProperty(value = "来源方法")
    private String sourceMethod;
}

