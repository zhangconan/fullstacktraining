package com.zkn.fullstacktraining.spring.one.controller;

import com.zkn.fullstacktraining.spring.one.annotation.CustomController;
import com.zkn.fullstacktraining.spring.one.annotation.CustomRequestMapping;

/**
 * Created by zkn on 2017/7/29.
 */
@CustomController
@CustomRequestMapping(value = "/custom")
public class FirstPageController {

    @CustomRequestMapping(value = "/myFirstPage.do")
    public void myFirstPage() {
        System.out.println("我被调用了、、、、");
    }
}
