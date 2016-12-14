package com.zkn.fullstacktraining.first.test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class StringCodeTest {

    @Test
    public void testUTF8ToGBK(){
        String str = "中国";
        try {
            String utf8 = new String(str.getBytes("utf-8"));
            System.out.println(utf8);
            String unicode = new String(utf8.getBytes(),"UTF-8");
            System.out.println(unicode);
            String gbk = new String(unicode.getBytes("GBK"));
            System.out.println(gbk);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testString(){
        String str = "qweerr";
        System.out.println(str.substring(0,str.length()));
    }
}
