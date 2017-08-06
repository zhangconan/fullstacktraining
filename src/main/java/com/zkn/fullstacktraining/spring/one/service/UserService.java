package com.zkn.fullstacktraining.spring.one.service;

import com.zkn.fullstacktraining.spring.one.domain.UserScope;

/**
 * Created by zkn on 2017/7/29.
 */
public interface UserService {
    /**
     * 插入用户信息
     * @param userScope
     * @return
     */
    Integer insert(UserScope userScope);
}
