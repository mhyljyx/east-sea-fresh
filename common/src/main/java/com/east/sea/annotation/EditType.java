package com.east.sea.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编辑类型注解
 * 用于标识字段在新增或修改时的必填校验
 *
 * @author tztang
 * @since 2026/02/27
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EditType {

    Type[] value() default {};

    enum Type {
        INSERT,
        UPDATE
    }

}
