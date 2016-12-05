package com.zkn.fullstacktraining.first;

import java.util.Arrays;

/**
 * Created by zkn on 2016/12/4.
 */
public class BubbleSortTest {

    public static void main(String[] args){
        int[] a = {3,1,10,9,100,7,4,-2,6,2};
        testBubbleSort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 冒泡排序
     * @param salaries
     */
    private static void testBubbleSort(int[] salaries) {
        for(int i=0;i<salaries.length-1;i++){
            for(int j=i;j<salaries.length;j++){
                int tmp = 0;
                if(salaries[i] < salaries[j]){
                    tmp = salaries[i];
                    salaries[i] = salaries[j];
                    salaries[j] = tmp;
                }
            }
        }
    }
}
