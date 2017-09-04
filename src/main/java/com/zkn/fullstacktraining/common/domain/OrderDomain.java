package com.zkn.fullstacktraining.common.domain;

import java.io.Serializable;

/**
 * Created by zkn on 2017/9/4.
 */
public class OrderDomain implements Serializable {
    /**
     * 订单类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderDomain{" +
                "type='" + type + '\'' +
                '}';
    }
}
