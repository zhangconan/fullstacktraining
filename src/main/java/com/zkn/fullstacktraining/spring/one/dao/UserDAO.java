package com.zkn.fullstacktraining.spring.one.dao;

import com.zkn.fullstacktraining.spring.one.domain.UserScope;

/**
 * Created by zkn on 2017/8/6.
 */
public interface UserDAO {
    /**
     * 插入用户信息
     * @param user
     * @return
     */
    Integer insert(UserScope user);
}
