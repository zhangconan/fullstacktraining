package com.zkn.fullstacktraining.second.decorate;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class ParticularOutputString implements OutputString{

    private OutputString outputString;

    /**
     * 处理字符串
     * @param str
     * @return
     */
    @Override
    public String processString(String str) {

        System.out.println("处理前的字符串:"+str);
        return outputString.processString(str);
    }

    public ParticularOutputString(OutputString outputString) {

        this.outputString = outputString;
    }
}
