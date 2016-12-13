package com.zkn.fullstacktraining.second.decorate;

import com.zkn.fullstacktraining.util.StringUtils;

import java.util.Base64;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 * 字符串加密操作
 */
public class DecorateOutputStringEncrypt extends DecorateOutputString {

    private OutputString outputString;

    /**
     * 处理字符串
     *
     * @param str
     * @return
     */
    @Override
    public String processString(String str) {
        if (!StringUtils.isEmpty(str)) {
            System.out.println("加密处理:"+str);
            Base64.Encoder base64 = Base64.getEncoder();
            str = new String(base64.encode(str.getBytes()));
            System.out.println("加密处之后:"+str);
            return outputString.processString(str);
        }
        return null;
    }

    public DecorateOutputStringEncrypt(OutputString outputString) {
        this.outputString = outputString;
    }

}
