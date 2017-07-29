package com.zkn.fullstacktraining.spring.one.controller;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zkn on 2017/7/29.
 */
public class RequestMappingInfo {
    /**
     * 类名
     */
    private Class<?> clazz;
    /**
     * 方法名
     */
    private Method method;
    /**
     * 对象
     */
    private Object obj;
    /**
     * 参数
     */
    private List<String> parameter;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<String> getParameter() {
        return parameter;
    }

    public void setParameter(List<String> parameter) {
        this.parameter = parameter;
    }
}
