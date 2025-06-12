package com.east.sea.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 必须字段自动填充
 * @author tztang
 * @since 2025-01-10
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 检查字段是否存在
        if (metaObject.hasSetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", DateUtil::date, Date.class);
        }
        if (metaObject.hasSetter("updateTime")) {
            this.strictInsertFill(metaObject, "updateTime", DateUtil::date, Date.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 检查字段是否存在
        if (metaObject.hasSetter("updateTime")) {
            //强制覆盖
            this.setFieldValByName("updateTime", DateUtil.date(), metaObject);
        }
    }

}