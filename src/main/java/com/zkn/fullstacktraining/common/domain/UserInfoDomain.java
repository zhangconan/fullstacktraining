package com.zkn.fullstacktraining.common.domain;

import java.io.Serializable;

/**
 * Created by zkn on 2017/9/4.
 */
public class UserInfoDomain implements Serializable {
    /**
     * 用户名
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserInfoDomain{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
