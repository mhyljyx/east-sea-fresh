package com.east.sea.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.east.sea.pojo.params.PageParams;

/**
 * 分页工具类
 * @author tztang
 * @since 2025/06/11
 */
public class PageUtil {

    // 构建分页对象
    public static <T> Page<T> getPage(PageParams params) {
        return new Page<>(params.getPageIndex(), params.getPageSize());
    }

}
