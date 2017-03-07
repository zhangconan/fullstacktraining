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
    /**
     * 获取uri
     * @param str
     * @return
     */
    public static String parserUri(String str,String spliter) {

        if(isEmpty(str)) {
            return "";
        }
        int indexFirst = str.indexOf(spliter);
        //说明查找到了
        if(indexFirst != -1){
            int indexSecond = str.indexOf(spliter,indexFirst+1);
            if(indexSecond > indexFirst) {
                return str.substring(indexFirst + 1, indexSecond);
            }
        }
        return "";
    }
}
