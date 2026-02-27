package com.east.sea.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

/**
 * 表必要字段
 * @author tztang
 * @since 2025/06/10
 **/
public class BaseTablePropertyEntity {

    /**
     * 编辑时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 是否删除: 0.未删除 1.已删除
     */
    @TableLogic
    private String isDel;

}
