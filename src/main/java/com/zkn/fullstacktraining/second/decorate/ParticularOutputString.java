package com.zkn.fullstacktraining.second.decorate;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class ParticularOutputString implements OutputString{

    /**
     * 处理字符串
     * @param str
     * @return
     */
    @Override
    public String processString(String str) {
        System.out.println("最终的结果："+str);
        return str;
    }
}
