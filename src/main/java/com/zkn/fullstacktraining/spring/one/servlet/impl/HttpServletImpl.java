package com.zkn.fullstacktraining.spring.one.servlet.impl;

import com.zkn.fullstacktraining.spring.one.context.ApplicationContext;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.process.StaticResourceProcessor;
import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import org.apache.commons.lang.StringUtils;
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
                if (clazzs != null) {
                    for (int i = 0; i < clazzs.length; i++) {
                        //如果参数类型为Request
                        if (clazzs[i].isAssignableFrom(Request.class)) {
                            list.add(request);
                        } else if (clazzs[i].isAssignableFrom(Response.class)) {
                            //如果参数类型为Response
                            list.add(response);
                        }
                        //如果是字节类型(包含基本类型和包装类)
                        else if (clazzs[i] == byte.class || clazzs[i] == Byte.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Byte.parseByte(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是short类型(包含基本类型和包装类)
                        else if (clazzs[i] == short.class || clazzs[i] == Short.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Short.parseShort(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是char类型(包含基本类型和包装类)
                        else if (clazzs[i] == char.class || clazzs[i] == Character.class) {
                            list.add(null);
                        }
                        //如果是整型(包含基本类型和包装类)
                        else if (clazzs[i] == int.class || clazzs[i] == Integer.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Integer.parseInt(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是float(包含基本类型和包装类)
                        else if (clazzs[i] == float.class || clazzs[i] == Float.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Float.parseFloat(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是double(包含基本类型和包装类)
                        else if (clazzs[i] == double.class || clazzs[i] == Double.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Double.parseDouble(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是double(包含基本类型和包装类)
                        else if (clazzs[i] == long.class || clazzs[i] == Long.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Long.parseLong(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是boolean(包含基本类型和包装类)
                        else if (clazzs[i] == boolean.class || clazzs[i] == Boolean.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(Boolean.parseBoolean(request.getParameterValue(parameterList.get(i))));
                            }
                        }
                        //如果是boolean(包含基本类型和包装类)
                        else if (clazzs[i] == String.class) {
                            if (parLen < i) {
                                list.add(null);
                            } else {
                                list.add(request.getParameterValue(parameterList.get(i)));
                            }
                        }
                        //如果是日期类型,先不做处理
                        else if (clazzs[i] == Date.class) {
                            list.add(new Date());
                        } else {
                            //暂不考虑数组、集合、Map等类型
                            list.add(clazzs[i].newInstance());
                        }
                    }
                }
                mappingInfo.getMethod().invoke(mappingInfo.getObj(),list.toArray());
                response.sendSuccess();
            }
        }
    }

    @Override
    public void destory() {

    }
}
