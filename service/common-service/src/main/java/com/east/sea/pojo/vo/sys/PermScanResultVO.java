package com.east.sea.pojo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "权限扫描结果")
public class PermScanResultVO implements Serializable {

    @ApiModelProperty(value = "发现的权限数")
    private Integer found;

    @ApiModelProperty(value = "插入的权限数")
    private Integer inserted;

    @ApiModelProperty(value = "更新的权限数")
    private Integer updated;

    @ApiModelProperty(value = "权限项列表")
    private List<PermItemVO> items;
}

