package com.zkn.fullstacktraining.first;

/**
 * Created by zkn on 2016/11/27.
 * 位运算是针对整形数据的。
 * byte类型：8位1个字节。
 * char类型：16位2个字节
 * short类型：16位2个字节
 * int类型：32位4个字节
 * fload类型：32位4个字节
 * long类型：64位8个字节
 * double类型：64位8个字节
 * int强制转换成byte的原理是：保留最低的八位，符号位不变，取反再+1
 */
public class ByteCaculator {

    public static void main(String[] args) {
        byte aa = 127;
        System.out.println(Byte.MAX_VALUE);
        byte bb = (byte)(aa << 2);
        System.out.println(bb);
    }
    //int转换成数组
    public static byte[] getByte(int value){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(value & 0xFF);
        bytes[1] = (byte)(value >>8 & 0xFF);
        bytes[2] = (byte)(value >>16 & 0xFF);
        bytes[3] = (byte)(value >>24 & 0xFF);
        return bytes;
    }
}
