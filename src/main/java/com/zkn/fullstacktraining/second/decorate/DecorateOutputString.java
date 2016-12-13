package com.zkn.fullstacktraining.second.decorate;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public abstract class DecorateOutputString implements OutputString{

    @Override
    public String processString(String str) {

        return str;
    }
}
