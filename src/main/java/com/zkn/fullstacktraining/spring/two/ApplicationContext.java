package com.zkn.fullstacktraining.spring.two;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zkn.fullstacktraining.spring.one.annotation.CustomService;
import com.zkn.fullstacktraining.spring.two.domain.BeanInfo;
import com.zkn.fullstacktraining.util.StringUtils;
import org.reflections.Reflections;

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
     * 所有被管理的bean
     */
    private final static Set<Class<?>> allClass = Sets.newHashSet();

    public static void init(String scanPackage) throws IllegalAccessException, InstantiationException {

        if (StringUtils.isEmpty(scanPackage)) {
            throw new RuntimeException("扫描包路径不能为空!");
        }
        Reflections reflections = new Reflections(scanPackage);
        scanCustomService(reflections);
        //注入属性值
    }

    /**
     * 扫描带有CustomService注解的类
     *
     * @param reflections
     */
    private static void scanCustomService(Reflections reflections) throws InstantiationException, IllegalAccessException {
        //获取所有带CustomService注解的Service
        serviceClass.addAll(reflections.getTypesAnnotatedWith(CustomService.class));
        allClass.addAll(serviceClass);
        for (Iterator it = serviceClass.iterator(); it.hasNext(); ) {
            wrapperBeanInfo((Class<?>) it.next());
        }
    }

    /**
     * 组装BeanInfo
     *
     * @param clazz
     */
    private static void wrapperBeanInfo(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz == null || clazz == Object.class) {
            return;
        }
        //说明不在任何一个里面
        if (singleMap.get(clazz) == null && multiMap.get(clazz) == null) {
            BeanInfo beanInfo = getBeanInfo(clazz);
            singleMap.put(clazz, beanInfo);
        }
    }

    private static BeanInfo getBeanInfo(Class<?> clazz) throws InstantiationException, IllegalAccessException {
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
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        if (parameterizedType != null) {
            beanInfo.setActualType((Class) parameterizedType.getActualTypeArguments()[0]);
        }
        //实现的接口是否有泛型
        //@TODO
        Type[] types = clazz.getGenericInterfaces();
        //设置bean的名称
        String beanName = clazz.getAnnotation(CustomService.class).value();
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
    private static void wrapperSuperBeanInfo(BeanInfo beanInfo, Class<?> superClass) {
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
    private static void wrapperAllSuperBeanInfo(BeanInfo beanInfo, Class<?> superClass) {
        //两个里面都没有这种类型的bean
        if (singleMap.get(superClass) == null && multiMap.get(superClass) == null) {
            singleMap.put(superClass, beanInfo);
        }
        //如果singleMap中已经存在这个类型的bean了
        if (singleMap.get(superClass) != null) {
            List<BeanInfo> multyList = new ArrayList<>(2);
            multyList.add(beanInfo);
            multiMap.put(superClass, multyList);
            singleMap.remove(superClass);
        }
        //说明已经存在这种类型的bean了
        if (singleMap.get(superClass) == null && multiMap.get(superClass) != null) {
            multiMap.get(superClass).add(beanInfo);
        }
    }
}
