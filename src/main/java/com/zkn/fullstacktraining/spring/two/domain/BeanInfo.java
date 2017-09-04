package com.zkn.fullstacktraining.spring.two.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zkn on 2017/9/4.
 */
public class BeanInfo implements Serializable {
    private static final long serialVersionUID = -5670801509873040770L;
    /**
     * bean的名字
     */
    private String beanName;
    /**
     * 是否是接口
     */
    private boolean isInterface;
    /**
     * 是否是抽象类
     */
    private boolean isAbstract;
    /**
     * 父类的信息
     */
    private List<Class<?>> parentClass;
    /**
     * 类的信息
     */
    private Class clazz;
    /**
     * 对象
     */
    private Object object;
    /**
     * 实际类型
     */
    private Class actualType;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public List<Class<?>> getParentClass() {
        return parentClass;
    }

    public void setParentClass(List<Class<?>> parentClass) {
        this.parentClass = parentClass;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class getActualType() {
        return actualType;
    }

    public void setActualType(Class actualType) {
        this.actualType = actualType;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
