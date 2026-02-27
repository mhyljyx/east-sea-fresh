package com.east.sea.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.east.sea.common.ApiResponse;
import com.east.sea.pojo.vo.PageResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value="PageApiResponse",description="数据响应规范结构")
public class PageApiResponseUtil implements Serializable {

    public static <T> ApiResponse<PageResult<T>> buildPageApiResponse(IPage<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRecords(page.getRecords());
        pageResult.setTotal(page.getTotal());
        pageResult.setSize(page.getSize());
        pageResult.setCurrent(page.getCurrent());
        pageResult.setPages(page.getPages());
        return ApiResponse.ok(pageResult);
    }

}
