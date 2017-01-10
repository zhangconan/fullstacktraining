package com.zkn.fullstacktraining.fifth.variable;

import java.util.concurrent.atomic.LongAdder;

/**
 * Created by zkn on 2017/1/9.
 */
public class LongAdderVariable implements MyCounter{

    private LongAdder longAdder = new LongAdder();

    public void incr(){
        longAdder.increment();
    }
    public long getCurValue(){
        return longAdder.longValue();
    }
}
