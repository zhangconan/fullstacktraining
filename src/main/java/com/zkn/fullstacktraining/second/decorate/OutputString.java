package com.zkn.fullstacktraining.second.decorate;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 * 装饰者共同的接口
 */
public interface OutputString {
    /**
     * 处理字符串
     * @param str
     * @return
     */
    public String processString(String str);
}
