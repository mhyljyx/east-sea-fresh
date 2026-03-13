package com.east.sea.pojo.dto.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "权限扫描请求")
public class PermScanRequestDTO implements Serializable {

    @ApiModelProperty(value = "是否持久化生成的F级权限到sys_menu", example = "true")
    private Boolean persist;

    @ApiModelProperty(value = "当找不到父级C页面时的默认parentId，默认为0", example = "0")
    private Long defaultParentId;

    @ApiModelProperty(value = "清单中的目录/页面节点，先导入后绑定F级权限")
    private List<MenuNodeDTO> manifest;
}

