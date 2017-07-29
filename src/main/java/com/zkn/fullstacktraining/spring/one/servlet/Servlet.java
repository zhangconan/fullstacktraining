package com.zkn.fullstacktraining.spring.one.servlet;

import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;

/**
 * Created by zkn on 2017/7/29.
 */
public interface Servlet {

    void init();

    void service(Request request, Response response);

    void destory();
}
