package com.east.sea.util;

import com.east.sea.annotation.EditType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 属性赋值工具类，用于复制对象属性
 * @author tztang
 * @since 2025/06/13
 */
public class CopyUtil {

    /**
     * 根据目标对象字段上的 @EditType 注解及指定类型 etype，从源对象复制属性值到目标对象。
     *
     * <p>
     * 复制规则如下：
     * <ul>
     *   <li>仅处理目标对象中带有 {@link EditType} 注解的字段；</li>
     *   <li>若字段注解中包含传入的 etype（如 "modify"），则执行复制；</li>
     *   <li>字段名在源对象中必须存在，且字段类型必须一致；</li>
     *   <li>若目标字段没有注解或注解不包含 etype，则跳过该字段；</li>
     *   <li>源对象未声明该字段或类型不匹配，则不复制；</li>
     * </ul>
     *
     * 示例用法：
     * <pre>{@code
     *     TbPlace from = new TbPlace();
     *     from.setName("新名称");
     *
     *     TbPlace target = new TbPlace();
     *     Utils.copyPropertys(from, target, "modify");
     * }</pre>
     *
     * @param from   源对象（不需要加注解）
     * @param target 目标对象（字段上带有 @EditType 注解）
     * @param types  控制字段是否复制的操作类型（如 "create"、"modify"）
     *
     * @throws RuntimeException 如果字段访问失败或赋值异常
     */
    public static void copyProperties(Object from, Object target, String... types) {
        if (from == null || target == null || types == null || types.length == 0) {
            return;
        }
        List<String> typeList = Arrays.asList(types);
        for (Field targetField : target.getClass().getDeclaredFields()) {
            EditType editType = targetField.getAnnotation(EditType.class);
            // 如果没有标注，直接跳过
            if (editType == null) {
                continue;
            }
            // 判断是否匹配任意一种类型
            boolean match = Arrays.stream(editType.value()).anyMatch(typeList::contains);
            if (!match) {
                continue;
            }
            try {
                Field fromField = from.getClass().getDeclaredField(targetField.getName());
                if (!fromField.getType().equals(targetField.getType())) {
                    continue;
                }
                fromField.setAccessible(true);
                targetField.setAccessible(true);
                Object value = fromField.get(from);
                if (value != null) {
                    targetField.set(target, value);
                }
            } catch (NoSuchFieldException ignored) {
            } catch (Exception e) {
                throw new RuntimeException("复制属性失败: " + targetField.getName(), e);
            }
        }
    }

    /**
     * 简单复制两个对象中同名、同类型字段的值。
     * <p>
     * 复制规则如下：
     * <ul>
     *   <li>只复制目标对象中声明的字段（不含父类字段）；</li>
     *   <li>字段名在源对象中必须存在，且字段类型必须一致；</li>
     *   <li>字段访问使用反射，并跳过私有字段访问限制；</li>
     *   <li>源对象中不存在该字段时跳过，不会报错；</li>
     * </ul>
     *
     * <p>示例用法：
     * <pre>{@code
     *     UserDto from = new UserDto();
     *     UserEntity target = new UserEntity();
     *     CopyUtil.copyProperties(from, target);
     * }</pre>
     *
     * @param from   源对象（提供字段值）
     * @param target 目标对象（被赋值）
     * @param <T>    对象类型
     *
     * @throws RuntimeException 如果字段访问或赋值时出错
     */
    public static <T> void copyProperties(T from, T target) {
        for (Field targetField : target.getClass().getDeclaredFields()) {
            try {
                Field fromField = from.getClass().getDeclaredField(targetField.getName());
                if (!fromField.getType().equals(targetField.getType())) continue;
                fromField.setAccessible(true);
                targetField.setAccessible(true);
                targetField.set(target, fromField.get(from));
            } catch (NoSuchFieldException ignored) {
                // from 对象没有该字段，跳过
            } catch (Exception e) {
                throw new RuntimeException("复制属性失败: " + targetField.getName(), e);
            }
        }
    }

}
