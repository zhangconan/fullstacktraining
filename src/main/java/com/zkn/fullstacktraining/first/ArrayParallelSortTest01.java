package com.zkn.fullstacktraining.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by wb-zhangkenan on 2016/12/6.
 */
public class ArrayParallelSortTest01 {

    public static void main(String[] args){

        Random random = new Random();
        int[] ints = new int[8193];
        for(int i =0;i<8193;i++){
            ints[i] = random.nextInt();
        }
        Arrays.parallelSort(ints);
        System.out.println(Arrays.toString(ints));
    }
}
