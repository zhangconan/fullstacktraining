package com.zkn.fullstacktraining.spring.one.servlet;

import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zkn on 2017/7/29.
 */
public interface Servlet {

    void init();

    void service(Request request, Response response) throws InvocationTargetException, IllegalAccessException, InstantiationException, Exception;

    void destory();
}
