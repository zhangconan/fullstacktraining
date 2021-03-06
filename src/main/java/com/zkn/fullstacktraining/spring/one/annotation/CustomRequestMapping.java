package com.zkn.fullstacktraining.spring.one.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zkn on 2017/7/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface CustomRequestMapping {

    /**
     * URI
     * @return
     */
    String value();
    /**
     * 参数信息
     * @return
     */
    String[] parameter() default "";
}
