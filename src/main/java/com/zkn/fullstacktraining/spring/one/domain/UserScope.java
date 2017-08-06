package com.zkn.fullstacktraining.spring.one.domain;

import java.io.Serializable;

/**
 * Created by zkn on 2017/8/6.
 */
public class UserScope implements Serializable{

    private static final long serialVersionUID = -8340887359752275426L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "UserScope{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
