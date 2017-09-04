package com.zkn.fullstacktraining.spring.two;

/**
 * Created by zkn on 2017/9/4.
 */
public abstract class AbstractDao<T> {

    public void testGeneric(T t) {
        System.out.println(t);
    }

}
