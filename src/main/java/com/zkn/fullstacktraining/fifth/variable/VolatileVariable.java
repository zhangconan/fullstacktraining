package com.zkn.fullstacktraining.fifth.variable;

/**
 * Created by zkn on 2017/1/9.
 */
public class VolatileVariable implements MyCounter{

    private volatile long value;

    public void incr(){

        value++;
    }
    public long getCurValue(){

        return value;
    }
}
