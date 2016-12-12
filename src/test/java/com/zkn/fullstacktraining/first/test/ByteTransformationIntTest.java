package com.zkn.fullstacktraining.first.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by zkn on 2016/12/11.
 * 在计算机系统中，数值一律用补码来表示和存储。
 *  正数的 原码 反码 补码都是它本身
 *  负数的 原码 是它本身 反码 符号位不变，其他位取反，补码为反码+1.
 *  0 也有正0和负0之分。 0的补码都是 0000 0000
 *  正0 的原码为 0000 0000 反码 0000 0000 补码 0000 0000
 *  负0 的原码为 1000 0000 反码 1111 1111 补码 0000 0000
 */
public class ByteTransformationIntTest {

    @Test
    public void byteTransformationInt(){

        int temp = 579;
        byte[] bytes = new byte[4];
        putInt(bytes,temp);//用一个长度为4的数组表示一个整型
        System.out.println(getInt(bytes));//从长度为4的数组中还原放入的整型
    }
    /**
     * 用一个长度为4的数组表示一个整数
     * 因为java中一个int类型占4个字节即八位，而一个byte类型占1个字节
     * 所以如果用byte表示一个整数的话，需要一个长度为4的byte数组。
     * 注意这里要用无符号右移
     * @param bytes
     * @param temp
     */
    public void putInt(byte[] bytes, int temp) {
        bytes[0] = (byte)(temp >>> 24) ;//bytes[0]表示一个int值的最高8位
        bytes[1] = (byte)(temp >>> 16);//bytes[1]表示一个int值的接下来的8位
        bytes[2] = (byte)(temp >>> 8);//bytes[2]表示一个int值的再接下来的8位
        bytes[3] = (byte)(temp);      //bytes[3]表示一个int值的最低8位
    }
    /**
     * 把一个长度为4的byte数组还原为整数
     * 注意这里一定要明确的区分开来一个int值的四个字节。
     * 每个字节之间一定要用括号()括起来
     * @param bytes
     * @return
     */
    public int getInt(byte[] bytes) {
        return (bytes[0] << 24)           | //还原int值最高8位
                ((bytes[1] & 0xff) << 16) | //还原int值接下来的8位
                ((bytes[2] & 0xff) << 8 ) |//还原int值再接下来的8位
                (bytes[3] & 0xff);         //还原int值的最低8位
    }
    @Test
    public void testIntToBytes(){

        byte[] bytes = new byte[]{14,78,12,-12};
        int tmp = putBytes(bytes);
        byte[] bytes1 = getBytes(tmp);
        System.out.println(Arrays.toString(bytes1));
    }

    /**
     * 这里可以根据自己的需要改变byte数组的长度，
     * 这里我用的是一个长度为4的字节数组
     * @return
     */
    public int putBytes(byte[] bytes){
        return (bytes[0] & 0xff) |
                ((bytes[1] & 0xff) << 8) |
                ((bytes[2] & 0xff) << 16) |
                ((bytes[3] & 0xff) << 24);
    }

    /**
     * 从整型转换字节数组
     * @param a
     * @return
     */
    public byte[] getBytes(int a){

        byte[] bytes = new byte[4];
        bytes[0] = (byte)(a);
        bytes[1] = (byte)(a >>> 8);
        bytes[2] = (byte)(a >>> 16);
        bytes[3] = (byte)(a >>> 24);
        return bytes;
    }
}
