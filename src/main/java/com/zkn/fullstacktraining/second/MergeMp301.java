package com.zkn.fullstacktraining.second;

import java.io.*;

/**
 * Created by zkn on 2016/12/11.
 */
public class MergeMp301 {

    public static void main(String[] args){

        try {

            FileInputStream fis01 = new FileInputStream(new File("C:\\Users\\zkn\\Downloads\\1.mp3"));
            FileInputStream fis02 = new FileInputStream(new File("C:\\Users\\zkn\\Downloads\\2.mp3"));
            FileInputStream fis03 = new FileInputStream(new File("C:\\Users\\zkn\\Downloads\\3.mp3"));
            //第二个参数这里要设置为true（追加的操作） 默认为false
            FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\zkn\\Downloads\\mp31.mp3"),true);
            byte[] bytes1 = new byte[1024];
            byte[] bytes2 = new byte[1024];
            byte[] bytes3 = new byte[1024];
            while ( fis01.read(bytes1) > 0){
                fos.write(bytes1);
            }
            while ( fis02.read(bytes2) > 0){
                fos.write(bytes2);
            }
            while ( fis03.read(bytes3) > 0){
                fos.write(bytes3);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
