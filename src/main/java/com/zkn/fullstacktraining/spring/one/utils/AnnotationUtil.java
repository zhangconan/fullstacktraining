package com.zkn.fullstacktraining.spring.one.utils;

import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.annotation.CustomController;

import java.lang.reflect.Field;

/**
 * Created by zkn on 2017/8/6.
 */
public class AnnotationUtil {

    /**
     * 判断是不是Controller类
     *
     * @param clazz
     * @return
     */
    public static boolean isHandler(Class<?> clazz) {
        return clazz.getAnnotation(CustomController.class) != null;
    }

    /**
     * 判断是不是组件类
     *
     * @param clazz
     * @return
     */
    public static boolean isComponent(Class clazz) {
        return clazz.getAnnotation(CustomComponent.class) != null;
    }

    /**
     * 字段上面是不是有注解
     * @param field
     * @return
     */
    public static boolean isAutowire(Field field) {
        return field.getAnnotation(CustomAutowire.class) != null;
    }
}
