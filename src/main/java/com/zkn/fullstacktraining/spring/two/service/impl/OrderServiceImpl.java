package com.zkn.fullstacktraining.spring.two.service.impl;

import com.zkn.fullstacktraining.common.domain.OrderDomain;
import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.two.AbstractDao;
import com.zkn.fullstacktraining.spring.two.service.OrderService;

/**
 * Created by zkn on 2017/9/4.
 */
@CustomService
public class OrderServiceImpl implements OrderService {

    @CustomAutowire
    private AbstractDao<OrderDomain> abstractDao;

    @Override
    public void testGeneric(OrderDomain orderDomain) {
        abstractDao.testGeneric(orderDomain);
    }
}
