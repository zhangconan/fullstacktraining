package com.zkn.fullstacktraining.second.decorate;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 * 字符串加密操作
 */
public class DecorateOutputStringEncrypt extends DecorateOutputString{

    private OutputString outputString;

    /**
     * 处理字符串
     *
     * @param str
     * @return
     */
    @Override
    public String processString(String str) {
        System.out.println("加密处理");
        return outputString.processString(str);
    }

    public DecorateOutputStringEncrypt(OutputString outputString) {
        this.outputString = outputString;
    }
}
