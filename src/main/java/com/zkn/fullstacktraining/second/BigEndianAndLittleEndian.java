package com.zkn.fullstacktraining.second;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        int tmp = 10240;
        /*
        下面开始的是BigEndian
         */
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("G:\\LearnVideo\\BigAndLittleEndian\\BigEndian.txt");
            //byte[0] = tmp
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
