package com.zkn.fullstacktraining.spring.one.context;

import com.zkn.fullstacktraining.spring.one.annotation.CustomController;
import com.zkn.fullstacktraining.spring.one.annotation.CustomRequestMapping;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.resource.CustomClasspathResource;
import com.zkn.fullstacktraining.spring.one.resource.CustomInputStreamSource;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zkn on 2017/7/29.
 */
public class ApplicationContext {

    public static final Map<String, RequestMappingInfo> mappingMap = new HashMap<>();
    private static Servlet servlet;
    private CustomInputStreamSource streamSource = null;

    public ApplicationContext(Servlet servlet, String location) {
        this.servlet = servlet;
        streamSource = new CustomClasspathResource(location);
    }

    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(streamSource.getInputStream());
        String componentScan = properties.getProperty("component.scan");
        String filePath = System.getProperty("user.dir");
        File file = new File(filePath);
        recursionFile(file, componentScan);
        servlet.init();
    }

    public void recursionFile(File file, String componentScan) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fi : files) {
                recursionFile(fi, componentScan);
            }
        }
        if (file.getPath().endsWith(".class")) {
            if (file.getPath().contains("." + componentScan + ".")) {
                Class clazz = Class.forName(file.getPath());
                if (isHandler(clazz)) {
                    Object obj = clazz.newInstance();
                    String str = "";
                    CustomRequestMapping customRequestMapping = null;
                    if ((customRequestMapping = isRequestMapping(clazz)) != null) {
                        if (customRequestMapping.value().startsWith("/")) {
                            str = customRequestMapping.value();
                        } else {
                            str = "/" + customRequestMapping.value();
                        }
                    }
                    Method[] methodArray = clazz.getMethods();
                    if (methodArray != null) {
                        RequestMappingInfo requestMappingInfo = null;
                        for (Method method : methodArray) {
                            customRequestMapping = method.getAnnotation(CustomRequestMapping.class);
                            if (customRequestMapping != null) {
                                String strInner = "";
                                requestMappingInfo = new RequestMappingInfo();
                                requestMappingInfo.setClazz(clazz);
                                requestMappingInfo.setMethod(method);
                                requestMappingInfo.setObj(obj);
                                if (customRequestMapping.value().startsWith("/")) {
                                    strInner = customRequestMapping.value();
                                } else {
                                    strInner = "/" + customRequestMapping.value();
                                }
                                String[] parameter = customRequestMapping.parameter();
                                if(parameter != null && parameter.length > 0){
                                    requestMappingInfo.setParameter(Arrays.asList(parameter));
                                }
                                mappingMap.put(str + strInner, requestMappingInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isHandler(Class<?> clazz) {
        return clazz.getAnnotation(CustomController.class) != null;
    }

    public CustomRequestMapping isRequestMapping(Class<?> clazz) {
        return clazz.getAnnotation(CustomRequestMapping.class);
    }

    public static Servlet getServlet(){
        return servlet;
    }
}
