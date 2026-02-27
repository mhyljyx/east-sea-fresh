package com.east.sea.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果结构体
 * @param <T> 泛型类型
 * @author tztang
 * @since 2025/06/13
 */
@Data
public class PageResult<T> implements Serializable {

    private List<T> records;

    private long size;

    private long current;

    private long total;

    private long pages;

}
