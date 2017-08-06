package com.zkn.fullstacktraining.spring.one.dao.impl;

import com.alibaba.fastjson.JSON;
import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.dao.UserDAO;
import com.zkn.fullstacktraining.spring.one.domain.UserScope;

import java.util.Random;

/**
 * Created by zkn on 2017/8/6.
 */
@CustomComponent
public class UserDAOImpl implements UserDAO {

    private Random random = new Random();

    @Override
    public Integer insert(UserScope user) {
        System.out.println("我是DAO层。。。。进行插入操作......");
        System.out.println(JSON.toJSONString(user));
        return random.nextInt();
    }
}
