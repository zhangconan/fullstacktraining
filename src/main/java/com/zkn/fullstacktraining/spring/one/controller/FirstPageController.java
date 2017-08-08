package com.zkn.fullstacktraining.spring.one.controller;

import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomController;
import com.zkn.fullstacktraining.spring.one.annotation.CustomRequestMapping;
import com.zkn.fullstacktraining.spring.one.domain.UserScope;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;
import com.zkn.fullstacktraining.spring.one.service.UserService;
import com.zkn.fullstacktraining.spring.one.servlet.Cookie;

/**
 * Created by zkn on 2017/7/29.
 */
@CustomController
@CustomRequestMapping(value = "/custom")
public class FirstPageController {

    @CustomAutowire
    private UserService userService;

    @CustomRequestMapping(value = "/myFirstPage.do")
    public void myFirstPage() {
        System.out.println("我被调用了、、、、");
    }

    /**
     * 插入操作
     *
     * @param userScope
     */
    @CustomRequestMapping(value = "/inserUser.do")
    public void inserUser(UserScope userScope, Response response) {
        Cookie cookie = new Cookie("test", "testcookie");
        Cookie cookie2 = new Cookie("test02", "testcookie02");
        response.addCookie(cookie);
        response.addCookie(cookie2);
        System.out.println("我是插入操作的Controller层、、、、");
        userService.insert(userScope);
    }
}
