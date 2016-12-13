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
        String gbk = "iteye问答频道编码转换问题";
        String iso = null;
        try {
            iso = new String(gbk.getBytes("UTF-8"),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(iso);
        String utf8 = null;
        try {
            utf8 = new String(iso.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(utf8);
    }
}
