package com.zkn.fullstacktraining.fifth.variable;

/**
 * Created by zkn on 2017/1/9.
 */
public class SynchronizeVariable implements MyCounter{

    private long value;

    public synchronized void incr(){

        this.value++;
    }
    public long getCurValue(){

        return this.value;
    }
}
