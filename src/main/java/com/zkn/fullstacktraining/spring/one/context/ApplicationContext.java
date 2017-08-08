package com.zkn.fullstacktraining.spring.one.context;

import com.google.common.collect.Lists;
import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.annotation.CustomController;
import com.zkn.fullstacktraining.spring.one.annotation.CustomRequestMapping;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.one.bean.BeanDefinitionInfo;
import com.zkn.fullstacktraining.spring.one.controller.RequestMappingInfo;
import com.zkn.fullstacktraining.spring.one.resource.CustomClasspathResource;
import com.zkn.fullstacktraining.spring.one.resource.CustomInputStreamSource;
import com.zkn.fullstacktraining.spring.one.servlet.Servlet;
import com.zkn.fullstacktraining.spring.one.utils.AnnotationUtil;
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
    private static final Map<Class<?>, BeanDefinitionInfo> allScanClazz = new HashMap<>();
    private static final List<Class<?>> allClass = Lists.newArrayList();

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
            Class<?> clazz = it.next();
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setClazz(clazz);
            wrapperSuperClass(clazz, beanDefinitionInfo);
            allScanClazz.put(clazz, beanDefinitionInfo);
            allClass.add(clazz);
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
        BeanDefinitionInfo beanDefinitionInfo = null;
        for (Map.Entry<Class<?>, BeanDefinitionInfo> entry : allScanClazz.entrySet()) {
            Class clazz = entry.getKey();
            beanDefinitionInfo = entry.getValue();
            instance = beanDefinitionInfo.getObject();
            if (instance == null) {
                instance = clazz.newInstance();
                beanDefinitionInfo.setObject(instance);
            }
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    if (!fields[i].isAccessible()) {
                        fields[i].setAccessible(true);
                    }
                    if (AnnotationUtil.isAutowire(fields[i])) {
                        Class tmpClass = fields[i].getType();
                        if (tmpClass.isInterface()) {
                            BeanDefinitionInfo tmpBean = null;
                            for (int j = 0; j < allClass.size(); j++) {
                                tmpBean = allScanClazz.get(allClass.get(j));
                                if (tmpClass.isAssignableFrom(tmpBean.getClazz())) {
                                    if (tmpBean.getObject() == null) {
                                        Object tmp = tmpBean.getClazz().newInstance();
                                        tmpBean.setObject(tmp);
                                    }
                                    fields[i].set(instance, tmpBean.getObject());
                                    break;
                                }
                            }
                        } else {
                            BeanDefinitionInfo tmpBean = allScanClazz.get(tmpClass);
                            if (tmpBean.getObject() == null) {
                                tmpBean.setObject(tmpBean.getClazz().newInstance());
                            }
                            fields[i].set(instance, tmpBean.getObject());
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
        BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
        beanDefinitionInfo.setClazz(clazz);
        beanDefinitionInfo.setObject(obj);
        wrapperSuperClass(clazz, beanDefinitionInfo);
        allScanClazz.put(clazz, beanDefinitionInfo);
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
                    requestMappingInfo = new RequestMappingInfo();
                    requestMappingInfo.setClazz(clazz);
                    requestMappingInfo.setMethod(method);
                    requestMappingInfo.setObj(obj);
                    Class<?>[] clazzs = method.getParameterTypes();
                    String strInner = "";
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

    /**
     * 组装父类
     *
     * @param clazz
     * @param beanDefinitionInfo
     */
    private void wrapperSuperClass(Class<?> clazz, BeanDefinitionInfo beanDefinitionInfo) {
        Class<?> tmp = clazz;
        List<Class<?>> superList = Lists.newArrayList();
        while (tmp.getSuperclass() != null && tmp.getSuperclass() != Object.class) {
            superList.add(clazz.getSuperclass());
            tmp = clazz.getSuperclass();
        }
        beanDefinitionInfo.setSuperClass(superList);
    }

    public CustomRequestMapping isRequestMapping(Class<?> clazz) {
        return clazz.getAnnotation(CustomRequestMapping.class);
    }

    public static Servlet getServlet() {
        return servlet;
    }
}
