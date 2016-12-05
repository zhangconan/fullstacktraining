package com.zkn.fullstacktraining.first;

/**
 * Created by zkn on 2016/11/28.
 * 位移运算：
 *      java中的位移运算分为三种：
 *          1、左移  <<  丢掉最高位，低位补0
 *          2、右移  >> 符号位（最高位）不变，移动的位置补0
 *          3、无符号右移 >>> 高位补0，其他为右移。
 */
public class ByteCaculator02 {

    public static void main(String[] args){
        int a = -1024;
        System.out.println(a >> 1);
        System.out.println(a >>> 1);
    }
}
