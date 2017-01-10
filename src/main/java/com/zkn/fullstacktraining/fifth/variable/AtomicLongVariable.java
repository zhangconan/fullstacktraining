package com.zkn.fullstacktraining.fifth.variable;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zkn on 2017/1/9.
 */
public class AtomicLongVariable implements MyCounter{

    private AtomicLong value = new AtomicLong(0);

    public void incr(){
        value.getAndIncrement();
    }
    public long getCurValue(){
        return value.longValue();
    }
}
