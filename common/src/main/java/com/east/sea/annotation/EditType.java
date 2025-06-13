package com.east.sea.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象内部属性赋值注解,用于控制指定属性赋值
 * @author tztang
 * @since 2025/06/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EditType {

    String INSERT = "insert"; //新增

    String MODIFY = "modify"; //修改

    String[] value(); // 例如 {"create"}, {"modify"}

}
