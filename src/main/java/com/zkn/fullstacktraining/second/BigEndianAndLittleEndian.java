package com.zkn.fullstacktraining.second;

import java.io.*;
import java.util.Arrays;

/**
 * Created by zkn on 2016/12/11.
 * Java中的大头（Big Endian，LittleEndian）小头问题：即多字节数据在网络上传输的时候，
 * 涉及到内存中存储字节顺序的问题。
 * 如数字 0x12345678在Big Endian和Little Endian中表示：
 * Big Endian: SUN、IBM的CPU
 *              低地址-------------------------->高地址
 *                  12 34 56 78
 * Little Endian：X86架构
 *              低地址----------------------->高地址
 *                  78 56 34 12
 *  Big Endian可以认为是现代人阅读的顺序，从左到右。Little Endian可以认为是古代人的读书顺序，从右到左。
 *  Java中是默认的Big Endian。
 */
public class BigEndianAndLittleEndian {

    public static void main(String[] args){
        bigEndian();
        littleEndian();
    }

    /**
     * 大头
     */
    public static void bigEndian(){
        int tmp = 10240;
        /*
        下面开始的是BigEndian
         */
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream("G:\\LearnVideo\\BigAndLittleEndian\\BigEndian.txt");
            //Big Endian的表示形式
            byte[] bytes = new byte[4];
            bigEndianBytes(bytes,tmp);
            System.out.println("BigEndian方式得到的字节数组："+Arrays.toString(bytes));
            fos.write(bytes,0,bytes.length);
            fos.flush();
            fis = new FileInputStream(new File("G:\\LearnVideo\\BigAndLittleEndian\\BigEndian.txt"));
            byte[] bytes1 = new byte[4];
            fis.read(bytes1);
            System.out.println("从文件中读取放入的字节值：   "+Arrays.toString(bytes1));
            System.out.println("还原BigEndian方式写入到文件中的值："+bigEndianInt(bytes1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * BigEndian的形式表示一个整型
     */
    public static void bigEndianBytes(byte[] bytes,int a){
        bytes[0] = (byte)(a >>> 24);
        bytes[1] = (byte)(a >>> 16);
        bytes[2] = (byte)(a >>> 8);
        bytes[3] = (byte)a;
    }
    /**
     * 还原用BigEndian方式存储在数组中的值
     * @param bytes
     * @return int
     */
    public static int bigEndianInt(byte[] bytes){

        return ((bytes[0] & 0xff) << 24) |
                ((bytes[1] & 0xff) << 16) |
                ((bytes[2] & 0xff ) << 8) |
                (bytes[3] & 0xff);
    }

    public static void littleEndian() {
        int tmp = 10240;
        /*
        下面开始的是LittleEndian
         */
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream("G:\\LearnVideo\\BigAndLittleEndian\\LittleEndian.txt");
            //Big Endian的表示形式
            byte[] bytes = new byte[4];
            littleEndianBytes(bytes,tmp);
            System.out.println("LittleEndian方式得到的字节数组："+Arrays.toString(bytes));
            fos.write(bytes,0,bytes.length);
            fis = new FileInputStream(new File("G:\\LearnVideo\\BigAndLittleEndian\\LittleEndian.txt"));
            byte[] bytes1 = new byte[4];
            fis.read(bytes1);
            System.out.println("从文件中读取放入的字节值：   "+Arrays.toString(bytes1));
            System.out.println("还原LittleEndian方式写入到文件中的值："+littleEndianBytes(bytes1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 还原用LittelEndian方式存储在数组中的值
     * @param bytes
     * @return int
     */
    private static int littleEndianBytes(byte[] bytes) {
        return (bytes[0] & 0xff) |
                ((bytes[1] & 0xff ) << 8) |
                ((bytes[2] & 0xff ) << 16) |
                ((bytes[3] & 0xff) << 24);
    }

    /**
     * LittleEndian的形式表示一个整型
     * @param bytes
     * @param tmp
     */
    public static void littleEndianBytes(byte[] bytes, int tmp) {
        bytes[0] = (byte) tmp;
        bytes[1] = (byte)(tmp >>> 8);
        bytes[2] = (byte)(tmp >>> 16);
        bytes[3] = (byte)(tmp >>> 24);
    }

}
