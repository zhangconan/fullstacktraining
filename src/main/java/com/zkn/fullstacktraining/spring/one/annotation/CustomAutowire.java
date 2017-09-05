package com.zkn.fullstacktraining.spring.one.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zkn on 2017/8/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomAutowire {
    /**
     * 姓名
     *
     * @return
     */
    String name() default "";

    /**
     * 是否是必须要注入
     *
     * @return
     */
    boolean isRequired() default true;
}
