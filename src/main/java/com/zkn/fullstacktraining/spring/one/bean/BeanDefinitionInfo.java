package com.zkn.fullstacktraining.spring.one.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wb-zhangkenan on 2017/8/8.
 *
 * @author wb-zhangkenan
 * @date 2017/08/08
 */
public class BeanDefinitionInfo implements Serializable {

    private static final long serialVersionUID = -5988012842492544219L;
    /**
     * 类信息
     */
    private Class<?> clazz;
    /**
     *
     */
    private Object object;
    /**
     * 父类
     */
    private List<Class<?>> superClass;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Class<?>> getSuperClass() {
        return superClass;
    }

    public void setSuperClass(List<Class<?>> superClass) {
        this.superClass = superClass;
    }
}
