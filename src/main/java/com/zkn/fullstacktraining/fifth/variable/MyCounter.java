package com.zkn.fullstacktraining.fifth.variable;

/**
 * Created by zkn on 2017/1/10.
 */
public interface MyCounter {
    /**
     * 增加的操作
     */
    void incr();
    /**
     * 获取结果
     * @return
     */
    long getCurValue();
}
