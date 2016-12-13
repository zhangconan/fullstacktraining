package com.zkn.fullstacktraining.util;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){

        if(str == null || str.length() == 0)
            return true;
        return false;
    }

}
