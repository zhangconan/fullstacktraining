package com.zkn.fullstacktraining.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author zkn
 * @date 2017/9/4
 */
@Getter
@Setter
@ToString
public class UserInfoDomain implements Serializable {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
}
