package com.zkn.fullstacktraining.spring.one.service.impl;

import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.dao.UserDAO;
import com.zkn.fullstacktraining.spring.one.domain.UserScope;
import com.zkn.fullstacktraining.spring.one.service.UserService;

/**
 * Created by zkn on 2017/7/29.
 */
@CustomComponent
public class UserServiceImpl implements UserService {

    @CustomAutowire
    private UserDAO userDAO;

    /**
     * 插入用户信息
     *
     * @param userScope
     * @return
     */
    @Override
    public Integer insert(UserScope userScope) {
        System.out.println("我是Service层。。。。进行插入操作......");
        return userDAO.insert(userScope);
    }
}
