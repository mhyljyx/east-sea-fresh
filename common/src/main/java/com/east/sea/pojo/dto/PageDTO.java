package com.east.sea.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页参数
 * @author tztang
 * @since 2025/06/10
 */
@Data
public class PageDTO extends SortDTO {

    @ApiModelProperty(value = "当前页码，默认值为1", example = "1L")
    private Long pageIndex = 1L;

    @ApiModelProperty(value = "每页条数，默认值为10", example = "10L")
    private Long pageSize = 10L;

}
