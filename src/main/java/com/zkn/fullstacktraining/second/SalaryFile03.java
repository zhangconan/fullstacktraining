package com.zkn.fullstacktraining.second;

import com.google.common.collect.Lists;
import com.zkn.fullstacktraining.first.Salary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wb-zhangkenan on 2016/12/15.
 */
public class SalaryFile03 {
    private static FileChannel fileChannel = null;
    private static RandomAccessFile rdf = null;
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(10240);

    public static void main(String[] args) {
        writeFile();
        readFile();
    }

    private static void readFile() {
        try {
            long startTime = System.currentTimeMillis();
            byte[] tmpBytes = null;
            rdf = new RandomAccessFile("D:\\LearnVideo\\text.txt", "r");
            fileChannel = rdf.getChannel();
            int count = 0;
            String tmpStr = "";//有可能一次读出来的数据不是以行结尾的，所以这里要进行拼接一下保证切割的都是一行的数据。
            List<String> strList = Lists.newArrayList();
            //
            Map<String, SalaryAssistScope> tmpMap = new HashMap<String, SalaryAssistScope>();
            while ((count = fileChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                tmpBytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(tmpBytes);
                String str = tmpStr + new String(tmpBytes, "utf-8");
                if (str.endsWith("\r\n")) {
                    String[] tmpStrs = str.split("\r\n");
                    for (int i = 0; i < tmpStrs.length; i++) {
                        String[] strs = tmpStrs[i].split(",");
                        String str1 = strs[0].substring(0, 2);
                        if (tmpMap.get(str1) != null) {
                            SalaryAssistScope temSalary = tmpMap.get(str1);
                            temSalary.setSalaryTotal(temSalary.getSalaryTotal() + Long.parseLong(strs[1]) * 13 + Long.parseLong(strs[2]));
                            temSalary.setCount(temSalary.getCount() + 1);
                        } else {
                            tmpMap.put(str1, new SalaryAssistScope(str1,
                                    Long.parseLong(strs[1]) * 13 + Long.parseLong(strs[2]), 1));
                        }
                    }
                    tmpStr = "";//重新置值
                } else {//读取到的不是一行的结尾
                    String[] tmpStrs = str.split("\r\n");
                    for (int i = 0; i < tmpStrs.length - 1; i++) {
                        String[] strs = tmpStrs[i].split(",");
                        String str1 = strs[0].substring(0, 2);
                        if (tmpMap.get(str1) != null) {
                            SalaryAssistScope temSalary = tmpMap.get(str1);
                            temSalary.setSalaryTotal(temSalary.getSalaryTotal() + Long.parseLong(strs[1]) * 13 + Long.parseLong(strs[2]));
                            temSalary.setCount(temSalary.getCount() + 1);
                        } else {
                            tmpMap.put(str1, new SalaryAssistScope(str1,
                                    Long.parseLong(strs[1]) * 13 + Long.parseLong(strs[2]), 1));
                        }
                    }
                    tmpStr = tmpStrs[tmpStrs.length - 1];//重新置值
                }
                byteBuffer.clear();
            }
            PriorityQueue<SalaryAssistScope> priorityQueue = new PriorityQueue<SalaryAssistScope>(10);
            for(Map.Entry<String,SalaryAssistScope> entry : tmpMap.entrySet()){
                priorityQueue.add(entry.getValue());
            }
            for(int i=0;i<10;i++){
                System.out.println(priorityQueue.poll());
            }
            System.out.println("FileChannel读取耗时："+(System.currentTimeMillis()-startTime));
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

    private static void writeFile() {
        try {
            rdf = new RandomAccessFile("D:\\LearnVideo\\text01.txt", "rw");
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
            System.out.println("FileChannel写入耗时：" + (System.currentTimeMillis() - startTime));
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

