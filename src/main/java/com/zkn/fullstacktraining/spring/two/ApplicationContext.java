package com.zkn.fullstacktraining.spring.two;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zkn.fullstacktraining.spring.one.annotation.CustomAutowire;
import com.zkn.fullstacktraining.spring.one.annotation.CustomComponent;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.two.domain.BeanInfo;
import com.zkn.fullstacktraining.util.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zkn on 2017/9/4.
 */
public class ApplicationContext {
    /**
     * 存放单个的Bean
     */
    private final static Map<Class<?>, BeanInfo> singleMap = Maps.newHashMap();
    /**
     * 存放多个Bean
     */
    private final static Map<Class<?>, List<BeanInfo>> multiMap = Maps.newHashMap();
    /**
     * 存放所有带CustomService的类
     */
    private final static Set<Class<?>> serviceClass = Sets.newHashSet();
    /**
     * 存放所有带CustomComponent注解的类
     */
    private final static Set<Class<?>> componentClass = Sets.newHashSet();
    /**
     * 所有被管理的bean
     */
    private final static Set<Class<?>> allClass = Sets.newHashSet();
    /**
     * 是否初始化过
     */
    private boolean isInit = false;

    public void init(String scanPackage) throws IllegalAccessException, InstantiationException {

        if (StringUtils.isEmpty(scanPackage)) {
            throw new RuntimeException("扫描包路径不能为空!");
        }
        Reflections reflections = new Reflections(scanPackage);
        scanCustomAnnotation(reflections);
        //注入属性值
        wrapperAutowired();
    }

    /**
     * 组装Autowire
     */
    private void wrapperAutowired() throws IllegalAccessException {
        if (!isInit) {
            throw new RuntimeException("还没有被初始化");
        }
        for (Iterator<Class<?>> it = allClass.iterator(); it.hasNext(); ) {
            Class<?> clazz = it.next();
            //这里只会有一个
            BeanInfo beanInfo = singleMap.get(clazz);
            if (beanInfo == null) {
                continue;
            }
            //获取所有的属性
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    CustomAutowire customAutowire = fields[i].getAnnotation(CustomAutowire.class);
                    if (customAutowire != null) {
                        Class<?> fieldClass = fields[i].getType();
                        BeanInfo fieldInfo = singleMap.get(fieldClass);
                        if (fieldInfo != null) {
                            fields[i].set(beanInfo.getObject(), fieldInfo.getObject());
                        } else {
                            List<BeanInfo> beanInfList = multiMap.get(fieldClass);
                            BeanInfo eleBeanInfo = null;
                            if (beanInfList != null) {
                                Type type = fields[i].getGenericType();
                                //说明有泛型
                                Class<?> actualClass = null;
                                if (type instanceof ParameterizedType) {
                                    actualClass = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                                }
                                //bean的名字
                                String beanName = customAutowire.name();
                                //是否是必须要注入值
                                boolean isRequired = customAutowire.isRequired();
                                boolean isAutowired = false;
                                for (int j = 0; j < beanInfList.size(); j++) {
                                    eleBeanInfo = beanInfList.get(j);
                                    //先根据名字注入
                                    if (!StringUtils.isEmpty(beanName)) {
                                        if (beanName.equals(eleBeanInfo.getBeanName())) {
                                            //说明已经注入过了
                                            if (isAutowired) {
                                                throw new RuntimeException(fieldClass.getName() + "类型bean的名字重复了!");
                                            }
                                            fields[i].set(beanInfo.getObject(), eleBeanInfo.getObject());
                                            isRequired = false;
                                            isAutowired = true;
                                        }
                                    } else {
                                        //根据泛型注入
                                        if (actualClass != null) {
                                            if (eleBeanInfo.getActualType() == actualClass) {
                                                //说明已经注入过了
                                                if (isAutowired) {
                                                    throw new RuntimeException(fieldClass.getName() + "类型bean的名字重复了!");
                                                }
                                                fields[i].set(beanInfo.getObject(), eleBeanInfo.getObject());
                                                isRequired = false;
                                                isAutowired = true;
                                            }
                                        }
                                    }
                                }
                                if (isRequired) {
                                    throw new RuntimeException(fieldClass.getName() + "类型,没有对应的实现类!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 扫描带有CustomService注解的类
     *
     * @param reflections
     */
    private void scanCustomAnnotation(Reflections reflections) throws InstantiationException, IllegalAccessException {
        //获取所有带CustomService注解的Service
        serviceClass.addAll(reflections.getTypesAnnotatedWith(CustomService.class));
        allClass.addAll(serviceClass);
        for (Iterator it = serviceClass.iterator(); it.hasNext(); ) {
            wrapperBeanInfo((Class<?>) it.next(), CustomService.class);
        }
        //所有CustomComponent注解的类
        componentClass.addAll(reflections.getTypesAnnotatedWith(CustomComponent.class));
        allClass.addAll(componentClass);
        for (Iterator it = componentClass.iterator(); it.hasNext(); ) {
            wrapperBeanInfo((Class<?>) it.next(), CustomComponent.class);
        }
        isInit = true;
    }

    /**
     * 组装BeanInfo
     *
     * @param clazz
     * @param annotationClass
     */
    private void wrapperBeanInfo(Class<?> clazz, Class<?> annotationClass) throws IllegalAccessException, InstantiationException {
        if (clazz == null || clazz == Object.class) {
            return;
        }
        //说明不在任何一个里面
        if (singleMap.get(clazz) == null && multiMap.get(clazz) == null) {
            BeanInfo beanInfo = getBeanInfo(clazz, annotationClass);
            singleMap.put(clazz, beanInfo);
        }
//        else if (singleMap.get(clazz) != null) {
//            List<BeanInfo> listBean = new ArrayList<>(2);
//            BeanInfo beanInfo = getBeanInfo(clazz, annotationClass);
//            listBean.add(beanInfo);
//            multiMap.put(clazz, listBean);
//            singleMap.remove(clazz);
//        } else if (singleMap.get(clazz) == null && multiMap.get(clazz) != null) {
//            BeanInfo beanInfo = getBeanInfo(clazz, annotationClass);
//            multiMap.get(clazz).add(beanInfo);
//        }
    }

    private BeanInfo getBeanInfo(Class<?> clazz, Class<?> annotationClass) throws InstantiationException, IllegalAccessException {
        BeanInfo beanInfo = new BeanInfo();
        //此class是否是接口
        if (clazz.isInterface()) {
            beanInfo.setInterface(true);
        }
        //此class是否是抽象类
        if (Modifier.isAbstract(clazz.getModifiers())) {
            beanInfo.setAbstract(true);
        }
        //所继承的类
        Class<?> superClass = clazz.getSuperclass();
        List<Class<?>> parentClass = Lists.newArrayList();
        if (superClass != Object.class) {
            parentClass.add(superClass);
            wrapperSuperBeanInfo(beanInfo, superClass);
        }
        //所有的接口
        Class[] classArray = clazz.getInterfaces();
        if (classArray != null && classArray.length > 0) {
            for (int i = 0; i < classArray.length; i++) {
                parentClass.add(classArray[i]);
                wrapperSuperBeanInfo(beanInfo, classArray[i]);
            }
        }
        beanInfo.setParentClass(parentClass);
        //查看继承的类是否有泛型
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            beanInfo.setActualType((Class) ((ParameterizedType) type).getActualTypeArguments()[0]);
        }
        //实现的接口是否有泛型
        //@TODO
        Type[] types = clazz.getGenericInterfaces();
        //设置bean的名称
        String beanName = "";
        if (annotationClass == CustomComponent.class) {
            beanName = clazz.getAnnotation(CustomComponent.class).name();
        }
        if (annotationClass == CustomService.class) {
            beanName = clazz.getAnnotation(CustomService.class).name();
        }
        if ("".equals(beanName)) {
            beanName = clazz.getSimpleName();
            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
            beanInfo.setBeanName(beanName);
        } else {
            beanInfo.setBeanName(beanName);
        }
        //创建实例对象
        if (!beanInfo.isInterface() && !beanInfo.isAbstract()) {
            beanInfo.setObject(clazz.newInstance());
        }
        return beanInfo;
    }

    /**
     * 组装父类BeanInfo
     *
     * @param beanInfo
     * @param superClass
     */
    private void wrapperSuperBeanInfo(BeanInfo beanInfo, Class<?> superClass) {
        Class<?> clazz = superClass;
        if (clazz == null) {
            return;
        }
        if (superClass.isInterface()) {
            wrapperAllSuperBeanInfo(beanInfo, superClass);
            Class<?>[] superClazz = superClass.getInterfaces();
            if (superClazz != null && superClazz.length > 0) {
                for (int i = 0; i < superClazz.length; i++) {
                    wrapperSuperBeanInfo(beanInfo, superClazz[i]);
                }
            }
        } else {
            while (clazz != Object.class) {
                wrapperAllSuperBeanInfo(beanInfo, superClass);
                clazz = clazz.getSuperclass();
                wrapperSuperBeanInfo(beanInfo, clazz);
            }
        }
    }

    /**
     * 组装存放beanInfo的Map
     *
     * @param beanInfo
     * @param superClass
     */
    private void wrapperAllSuperBeanInfo(BeanInfo beanInfo, Class<?> superClass) {
        //两个里面都没有这种类型的bean
        if (singleMap.get(superClass) == null && multiMap.get(superClass) == null) {
            singleMap.put(superClass, beanInfo);
        }
        //如果singleMap中已经存在这个类型的bean了
        else if (singleMap.get(superClass) != null) {
            List<BeanInfo> multyList = new ArrayList<>(2);
            multyList.add(beanInfo);
            multyList.add(singleMap.get(superClass));
            multiMap.put(superClass, multyList);
            singleMap.remove(superClass);
        }
        //说明已经存在这种类型的bean了
        else if (singleMap.get(superClass) == null && multiMap.get(superClass) != null) {
            multiMap.get(superClass).add(beanInfo);
        }
    }

    /**
     * 获取Bean
     *
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz) {
        if (!isInit) {
            throw new RuntimeException("系统还没有初始化!");
        }
        BeanInfo beanInfo = singleMap.get(clazz);
        if (beanInfo != null) {
            return beanInfo.getObject();
        }
        return null;
    }
}
