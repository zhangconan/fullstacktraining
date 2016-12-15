package com.zkn.fullstacktraining.second;

import com.zkn.fullstacktraining.first.Salary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wb-zhangkenan on 2016/12/15.
 */
public class SalaryFile03 {
    private static FileChannel fileChannel = null;
    private static RandomAccessFile rdf = null;
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    public static void main(String[] args) {
        writeFile();
        readFile();
    }

    private static void readFile(){
        try {
            byte[] tmpBytes = null;
            rdf = new RandomAccessFile("G:\\LearnVideo\\text01.txt", "r");
            fileChannel = rdf.getChannel();
            int count = 0;
            while((count = fileChannel.read(byteBuffer)) != -1){
                byteBuffer.flip();
                tmpBytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(tmpBytes);
                String str = new String(tmpBytes,"utf-8");
                System.out.print(str);
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (rdf != null)
                try {
                    rdf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeFile() {
        try {
            rdf = new RandomAccessFile("G:\\LearnVideo\\text01.txt", "rw");
            fileChannel = rdf.getChannel();
            Salary[] salaries = GenSalary.getSalary();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < salaries.length; i++) {
                byte[] bytes = (salaries[i].getFileLine() + "\r\n").getBytes("utf-8");
                //说明剩余的部分不足以放入这些元素
                if (byteBuffer.capacity() - byteBuffer.position() <= bytes.length) {
                    //翻转缓冲区，使缓冲区的内容传递给通道，被写出到设备上。
                    byteBuffer.flip();
                    //从通道写出内容到文件中
                    fileChannel.write(byteBuffer);
                    //清空缓冲区
                    byteBuffer.clear();
                }
                //在缓冲区中放入元素
                byteBuffer.put(bytes);
            }
            //将剩余的部分写出到文件中
            //翻转缓冲区
            byteBuffer.flip();
            //从通道写出内容到文件
            fileChannel.write(byteBuffer);
            //清空缓冲区
            byteBuffer.clear();
            System.out.println("耗时："+(System.currentTimeMillis()-startTime));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rdf != null)
                try {
                    rdf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

