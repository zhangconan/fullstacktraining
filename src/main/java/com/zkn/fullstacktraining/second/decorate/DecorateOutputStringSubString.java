package com.zkn.fullstacktraining.second.decorate;

import com.zkn.fullstacktraining.util.StringUtils;

/**
 * Created by zkn on 2016/12/13.
 */
public class DecorateOutputStringSubString extends DecorateOutputString {

    private OutputString outputString;

    public DecorateOutputStringSubString(OutputString outputString) {
        this.outputString = outputString;
    }

    @Override
    public String processString(String str) {
        if(!StringUtils.isEmpty(str)){
            System.out.println("截取字符串:"+str);
            if(str.length()<10){
                StringBuilder sb = new StringBuilder(str);
                for(int i=0;i<10-str.length();i++){
                    sb.append("!");
                }
                str = sb.toString();
            }else{
                str = str.substring(0,9);
            }
            System.out.println("截取字符串之后:"+str);
            return outputString.processString(str);
        }else{
            return null;
        }
    }

}
