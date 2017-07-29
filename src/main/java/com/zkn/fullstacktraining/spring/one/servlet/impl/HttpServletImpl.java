package com.zkn.fullstacktraining.spring.one.servlet.impl;

import com.zkn.fullstacktraining.spring.one.context.ApplicationContext;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.process.StaticResourceProcessor;
import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zkn on 2017/7/29.
 */
public class HttpServletImpl implements Servlet {

    private StaticResourceProcessor resouce = new StaticResourceProcessor();
    @Override
    public void init() {
        System.out.println("我被初始化了、、、、、");
    }

    @Override
    public void service(Request request, Response response) {
        String uri = request.getUri();
        if("/".equals(uri) || (!StringUtils.isEmpty(uri) && uri.startsWith("/static/"))){
            //处理静态资源
            resouce.process(request,response);
        }else{
            RequestMappingInfo mappingInfo = ApplicationContext.mappingMap.get(uri);
            Method method = mappingInfo.getMethod();
            try {
                mappingInfo.getMethod().invoke(mappingInfo.getObj());
                response.sendSuccess();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destory() {

    }
}
