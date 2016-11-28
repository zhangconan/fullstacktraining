package com.zkn.fullstacktraining.first;

import java.util.Random;

/**
 * Created by zkn on 2016/11/28.
 * 定义一个10240*10240的byte数组，
 *  分别采用行优先与列优先的循环方式来计算
 *  这些单元格的总和，看看性能的差距，并解释原因
 */
public class LoopArray {

    public static void main(String[] args){
        int length = 10240;
        byte[][] bytes = new byte[length][length];
        Random random = new Random();
        for(int i=0;i<length;i++){
            random.nextBytes(bytes[i]);
        }
        //行优先
        long timeStart = System.currentTimeMillis();
        int num1 = 0;
        for(int i=0;i<length;i++){
            for(int j=0;j<length;j++){
                num1 += bytes[i][j];
            }
        }
        System.out.println("耗时总时间："+(System.currentTimeMillis()-timeStart)+" num1的值 "+num1);
        int num2 = 0;
        timeStart = System.currentTimeMillis();
        //列优先
        for(int j=0;j<length;j++){
            for(int i=0;i<length;i++){
                num2 += bytes[i][j];
            }
        }
        System.out.println("耗时总时间："+(System.currentTimeMillis()-timeStart)+" num2的值 "+num2);
        /**
         * 二维数组是默认的存储模式是行优先存储，也就是每行的数据都是连续的，而每列的数据是不连续的。
         行优先遍历数组时，cpu 一次从主存中按行读取64byte 进cache中。减少了cpu与主存的交互次数，提高遍历效率。
         列有限遍历数组时，虽然cpu一次从主存中按行读取64byte 进cache 内存，然而，并没有命中。导致每次遍历都从主存中获取。
         就算整个数组都在内存中，列优先访问需要计算一次乘法，行优先只需要加一就可以了
         */
    }
}
