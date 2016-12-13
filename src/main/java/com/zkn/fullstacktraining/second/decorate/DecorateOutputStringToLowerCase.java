package com.zkn.fullstacktraining.second.decorate;

import com.zkn.fullstacktraining.util.StringUtils;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 * 字符串转换小写
 */
public class DecorateOutputStringToLowerCase extends DecorateOutputString{

    private OutputString outputString;

    public DecorateOutputStringToLowerCase(OutputString outputString) {
        this.outputString = outputString;
    }

    @Override
    public String processString(String str) {
        if(!StringUtils.isEmpty(str)){
            System.out.println("小写转换字符串:"+str);
            str.toLowerCase();
            System.out.println("小写转换字符串之后:"+str);
            return outputString.processString(str);
        }else{
            return null;
        }
    }

}
