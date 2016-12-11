package com.zkn.fullstacktraining.second;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by zkn on 2016/12/10.
 * 得到 String s="中国" 这个字符串的utf-8编码，gbk编码，
 * iso-8859-1编码的字符串，看看各自有多少字节，
 * 同时解释为什么以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
 * int转byte 保留最低八位 符号位不变 取反加一
 */
public class StringCode {

    public static void main(String[] args){
        //汉字"中国"的 GBK UNICODE UTF-8 编码对应如下：
        //         中                              国
        // GBK    UNICODE  UTF-8          GBK     UNICODE   UTF-8
        // D6D0   4E2D     E4 B8 AD       B9FA    56FD      E5 9B BD
        String str = "中国";
        try {
            //UTF-8的byte字节值  utf-8 汉字占三个字节
            System.out.println("UTF-8编码  "+Arrays.toString(str.getBytes("UTF-8")));
            //GBK编码   GBK是双字节编码
            System.out.println("GBK编码  "+Arrays.toString(str.getBytes("GBK")));
            //ISO-8859-1编码的字符串   属于单字节编码 表示范围为0-255 无法表示汉字
            //ISO-8859-1无法表示汉字，所以用ISO-8859-1得到的byte数组无法转换回去。即ISO-8859-1无法进行逆向操作。
            System.out.println("ISO-8859-1编码  "+Arrays.toString(str.getBytes("ISO-8859-1")));
            String strs = new String(str.getBytes("ISO-8859-1"),"ISO-8859-1");
            System.out.println(strs);
            //UTF-8中一个汉字占三个字节，GBK中一个汉字占两个字节。
            //中国的字节数组为[-28, -72, -83, -27, -101, -67] 取反+1得到的二进制为E4 B8 AD E5 9B BD
            //可以看出来这是六个字节，而GBK是两个字节表示一个汉字，所以E4 B8表示一个汉字，
            // AD E5表示一个汉字 9B BD表示一个汉字，通过查询GBK汉字编码对照表得到三个汉字为：涓浗
            //通过程序也可以看到输出结果为：涓浗。所以可以看出，utf-8编码得到的byte[]无法用gbk的方式“还原”
            // 为原来的字符串
            System.out.println(new String(str.getBytes("UTF-8"),"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
