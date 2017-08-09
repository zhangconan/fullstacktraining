package com.zkn.fullstacktraining.spring.one.servlet.impl;

import com.zkn.fullstacktraining.spring.one.context.ApplicationContext;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.process.StaticResourceProcessor;
import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void service(Request request, Response response) throws Exception {
        String uri = request.getUri();
        if ("/".equals(uri) || (!StringUtils.isEmpty(uri) && uri.startsWith("/static/"))) {
            //处理静态资源
            resouce.process(request, response);
        } else {
            RequestMappingInfo mappingInfo = ApplicationContext.mappingMap.get(uri);
            if (mappingInfo != null) {
                List<String> parameterList = mappingInfo.getParameter();
                int parLen = mappingInfo.getParameter().size() - 1;
                Class<?>[] clazzs = mappingInfo.getFormalParameter();
                List<Object> list = new ArrayList<>();
                Object obj = null;
                if (clazzs != null) {
                    for (int i = 0; i < clazzs.length; i++) {
                        obj = getObject(request, response, parameterList, parLen < i, clazzs[i], i);
                        list.add(obj);
                    }
                }
                mappingInfo.getMethod().invoke(mappingInfo.getObj(), list.toArray());
                response.sendSuccess();
            }
        }
    }

    /**
     * 组装方法的参数
     *
     * @param request
     * @param response
     * @param parameterList
     * @param b
     * @param clazz
     * @param i
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object getObject(Request request, Response response, List<String> parameterList, boolean b, Class<?> clazz, int i) throws InstantiationException, IllegalAccessException {
        Object obj = null;
        //如果参数类型为Request
        if (clazz.isAssignableFrom(Request.class)) {
            obj = request;
        } else if (clazz.isAssignableFrom(Response.class)) {
            //如果参数类型为Response
            obj = response;
        }
        //如果是字节类型(包含基本类型和包装类)
        else if (clazz == byte.class || clazz == Byte.class) {
            if (!b) {
                obj = Byte.parseByte(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是short类型(包含基本类型和包装类)
        else if (clazz == short.class || clazz == Short.class) {
            if (!b) {
                obj = Short.parseShort(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是char类型(包含基本类型和包装类)
        else if (clazz == char.class || clazz == Character.class) {

        }
        //如果是整型(包含基本类型和包装类)
        else if (clazz == int.class || clazz == Integer.class) {
            if (!b) {
                obj = Integer.parseInt(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是float(包含基本类型和包装类)
        else if (clazz == float.class || clazz == Float.class) {
            if (!b) {
                obj = Float.parseFloat(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是double(包含基本类型和包装类)
        else if (clazz == double.class || clazz == Double.class) {
            if (!b) {
                obj = Double.parseDouble(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是double(包含基本类型和包装类)
        else if (clazz == long.class || clazz == Long.class) {
            if (!b) {
                obj = Long.parseLong(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是boolean(包含基本类型和包装类)
        else if (clazz == boolean.class || clazz == Boolean.class) {
            if (!b) {
                obj = Boolean.parseBoolean(request.getParameterValue(parameterList.get(i)));
            }
        }
        //如果是boolean(包含基本类型和包装类)
        else if (clazz == String.class) {
            if (!b) {
                obj = request.getParameterValue(parameterList.get(i));
            }
        }
        //如果是日期类型,先不做处理
        else if (clazz == Date.class) {
            obj = new Date();
        } else {
            //暂不考虑数组、集合、Map等类型
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            wrapperObjectFieldValue(request, obj, fields);
        }
        return obj;
    }

    /**
     * 组装属性对象值
     *
     * @param request
     * @param obj
     * @param fields
     * @throws IllegalAccessException
     */
    private void wrapperObjectFieldValue(Request request, Object obj, Field[] fields) throws IllegalAccessException {
        if (fields != null) {
            Field field = null;
            for (int j = 0; j < fields.length; j++) {
                field = fields[j];
                String fieldName = field.getName();
                field.setAccessible(true);
                String value = request.getParameterValue(fieldName);
                if (value != null && !"".equals(value)) {
                    if (field.getType() == byte.class || field.getType() == Byte.class) {
                        field.set(obj, Byte.parseByte(value));
                    }
                    //如果是short类型(包含基本类型和包装类)
                    else if (field.getType() == short.class || field.getType() == Short.class) {
                        field.set(obj, Short.parseShort(value));
                    }
                    //如果是char类型(包含基本类型和包装类)
                    else if (field.getType() == char.class || field.getType() == Character.class) {
                        field.set(obj, value.toCharArray()[0]);
                    }
                    //如果是整型(包含基本类型和包装类)
                    else if (field.getType() == int.class || field.getType() == Integer.class) {
                        field.set(obj, Integer.parseInt((value)));
                    }
                    //如果是float(包含基本类型和包装类)
                    else if (field.getType() == float.class || field.getType() == Float.class) {
                        field.set(obj, Float.parseFloat((value)));
                    }
                    //如果是double(包含基本类型和包装类)
                    else if (field.getType() == double.class || field.getType() == Double.class) {
                        field.set(obj, Double.parseDouble((value)));
                    }
                    //如果是double(包含基本类型和包装类)
                    else if (field.getType() == long.class || field.getType() == Long.class) {
                        field.set(obj, Long.parseLong((value)));
                    }
                    //如果是boolean(包含基本类型和包装类)
                    else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        field.set(obj, Boolean.parseBoolean((value)));
                    }
                    //如果是boolean(包含基本类型和包装类)
                    else if (field.getType() == String.class) {
                        field.set(obj, value);
                    }
                }
            }
        }
    }

    @Override
    public void destory() {

    }
}
