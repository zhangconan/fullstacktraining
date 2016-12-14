package com.zkn.fullstacktraining.second.decorate;

import com.zkn.fullstacktraining.util.StringUtils;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 * 字符转换大写
 */
public class DecorateOutputStringToUpperCase extends DecorateOutputString {

    private OutputString outputString;

    public DecorateOutputStringToUpperCase(OutputString outputString) {
        this.outputString = outputString;
    }

    @Override
    public String processString(String str) {

        if(!StringUtils.isEmpty(str)){
            System.out.println("字符串大写转换："+str);
            str = str.toUpperCase();
            System.out.println("字符串大写转换之后:"+str);
            return outputString.processString(str);
        }else{
            return null;
        }
    }

}
