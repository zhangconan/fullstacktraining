package com.zkn.fullstacktraining.spring.one.context;

import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.annotation.CustomController;
import com.zkn.fullstacktraining.spring.one.annotation.CustomRequestMapping;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.resource.CustomClasspathResource;
import com.zkn.fullstacktraining.spring.one.resource.CustomInputStreamSource;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import com.zkn.fullstacktraining.spring.one.utils.AnnotationUtil;
import com.zkn.fullstacktraining.spring.one.utils.CommonConstant;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by zkn on 2017/8/8.
 */
public class ApplicationContext {


    public static final Map<String, RequestMappingInfo> mappingMap = new HashMap<>();
    /**
     * 所有扫描到的类
     */
    public static final Map<Class<?>, String> allScanClazz = new HashMap<>();
    /**
     * 所有的实例
     */
    public static final Map<Class<?>, Object> allInstance = new HashMap<>();
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
        wrapperCompontent(componentScan);
        imitateIOC();
        servlet.init();
    }

    private void wrapperCompontent(String componentScan) throws InstantiationException, IllegalAccessException {
        Reflections reflection = new Reflections(componentScan);
        //扫描所有有CustomController注解的类
        Set<Class<?>> controllerClazz = reflection.getTypesAnnotatedWith(CustomController.class);
        //扫描所有有CustomComponent注解的类
        Set<Class<?>> componentClazz = reflection.getTypesAnnotatedWith(CustomComponent.class);
        //扫描所有有CustomService注解的类
        Set<Class<?>> serviceClazz = reflection.getTypesAnnotatedWith(CustomService.class);
        for (Iterator<Class<?>> it = controllerClazz.iterator(); it.hasNext(); ) {
            wrapperController(it.next());
        }
        componentClazz.addAll(serviceClazz);
        for (Iterator<Class<?>> it = componentClazz.iterator(); it.hasNext(); ) {
            allScanClazz.put(it.next(), CommonConstant.COMPONENT);
        }
    }

    /**
     * 模仿IOC
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void imitateIOC() throws InstantiationException, IllegalAccessException {
        Object instance = null;
        for (Map.Entry<Class<?>, String> entry : allScanClazz.entrySet()) {
            Class clazz = entry.getKey();
            allInstance.get(clazz);
            if (instance == null) {
                instance = clazz.newInstance();
                allInstance.put(clazz, instance);
            }
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    if (!fields[i].isAccessible()) {
                        fields[i].setAccessible(true);
                    }
                    if (AnnotationUtil.isAutowire(fields[i])) {
                        Class tmpClass = fields[i].getType();
                        if (allScanClazz.get(tmpClass) != null) {
                            if (allScanClazz.get(tmpClass) != null) {
                                Object tmp = null;
                                if ((tmp = allInstance.get(tmpClass)) == null) {
                                    tmp = tmpClass.newInstance();
                                    allInstance.put(tmpClass, tmp);
                                }
                                fields[i].set(instance, tmp);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组装Controller
     *
     * @param clazz
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void wrapperController(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object obj = clazz.newInstance();
        allScanClazz.put(clazz, CommonConstant.CONTROLLER);
        allInstance.put(clazz, obj);
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
                    Class<?>[] clazzs = method.getParameterTypes();
                    if (clazzs != null) {
                        requestMappingInfo.setFormalParameter(clazzs);
                    }
                    if (customRequestMapping.value().startsWith("/")) {
                        strInner = customRequestMapping.value();
                    } else {
                        strInner = "/" + customRequestMapping.value();
                    }
                    String[] parameter = customRequestMapping.parameter();
                    if (parameter != null && parameter.length > 0) {
                        requestMappingInfo.setParameter(Arrays.asList(parameter));
                    }
                    mappingMap.put(str + strInner, requestMappingInfo);
                }
            }
        }
    }

    public CustomRequestMapping isRequestMapping(Class<?> clazz) {
        return clazz.getAnnotation(CustomRequestMapping.class);
    }

    public static Servlet getServlet() {
        return servlet;
    }
}
