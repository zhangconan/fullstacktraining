package com.zkn.fullstacktraining.spring.two.service.impl;

import com.zkn.fullstacktraining.common.domain.UserInfoDomain;
import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.two.AbstractDao;
import com.zkn.fullstacktraining.spring.two.service.UserService;

/**
 * Created by zkn on 2017/9/4.
 */
@CustomService
public class UserServiceImpl implements UserService {

    @CustomAutowire
    private AbstractDao<UserInfoDomain> abstractDao;

    @Override
    public void testGeneric(UserInfoDomain orderDomain) {
        System.out.println(abstractDao);
        abstractDao.testGeneric(orderDomain);
    }
}
