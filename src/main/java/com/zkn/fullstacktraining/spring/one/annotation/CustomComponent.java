package com.zkn.fullstacktraining.spring.one.annotation;

import java.lang.annotation.*;

/**
 * Created by zkn on 2017/7/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomComponent {
    /**
     * bean的名字
     *
     * @return
     */
    String name() default "";
}
