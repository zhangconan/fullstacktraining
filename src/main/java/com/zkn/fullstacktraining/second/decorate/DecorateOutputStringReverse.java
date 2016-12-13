package com.zkn.fullstacktraining.second.decorate;

import com.zkn.fullstacktraining.util.StringUtils;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class DecorateOutputStringReverse extends DecorateOutputString {

    private OutputString outputString;

    public DecorateOutputStringReverse(OutputString outputString) {
        this.outputString = outputString;
    }

    @Override
    public String processString(String str) {
        if(!StringUtils.isEmpty(str)){
            System.out.println("反转字符串:"+str);
            return super.processString(str);
        }else{
            return null;
        }
    }
}
