package com.east.sea.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 排序参数
 * @author tztang
 * @since 2025/06/10
 */
@Data
public class SortDTO {

    @ApiModelProperty(value = "排序参数列表", example = "[{ \"field\": \"name\", \"order\": \"ASC\" }]")
    private List<SortParam> sortParams; // 排序参数列表

    @Data
    static class SortParam {

        private String sortField; // 排序字段

        private String sortOrder; // 排序方式：asc/desc

    }

}
