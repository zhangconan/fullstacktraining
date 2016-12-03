package com.zkn.fullstacktraining.first;

import java.util.Arrays;

/**
 * Created by zkn on 2016/12/4.
 * 快速排序的思想：
 *  从一个数组中取出一个基数base
 *  然后把数组中的元素以base分为两部分。左边的是比base小的数据，右边的是比base大的数据。
 *  然后再对左右两边的数据进行分部分排序。主要用到了分治和递归的思想
 *  如下面这个数组：int[] a = {3,1,10,9,5,7,4,-2,6,2};
 *  base是每一部分的第一个元素，low是每一部分的数组开始的下标 high是每一部分数组最后的下标
 *  1、我们取base = 3 low = 0 high = 9
 *  2、我们从右向左进行排序：我们需要保证low要小于high，因为分成的两部分的内容不能交叉。循环主要用了while循环
 *      1)、a[high] = 2 （即a[6] = 2）小于 基数3
 *      2)、我们需要继续循环，此时high- high为8，a[high] = 6(即a[8]=6) 大于基数3
 *          此时我们需要把a[low]赋值为6 即a[0] = 6
 *      3)、接着我们把low++，low的值变为1
 *      4）、此时我们需要从左向右进行排序，排序需要while循环，此时a[low]=1 即a[1] = 1 小于基数 3 所以我们需要把a[low]的值赋给a[high]，即a[8] = a[1] = 1.
 *      5)、接着我们需要把high--，high的值变为7
 *      6）、接着开始1）的步骤 a[high]=-2 即a[7] = -2 小于基数 3 不变  此时我们需要把high-- high变为6 继续循环
 *      7）a[high] = 4 大于基数3 我们需要把a[high]赋值给a[low] 即把a[6]的值赋给a[1] 此时需要把low++ low的值为2
 *      8）、a[low]=10 即a[2]=10 不变  继续循环  此时我们需要把 low++  a[low] = 9 即a[3] = 9 大于基数3 不变继续循环 low++ low的值为4 a[low] = 5 即a[4]= 5大于3 不变继续循环
 *          low++ low的值为5 a[5] = 7 大于基数3不变 继续循环 low++ low的值为6 a[low]=4 即a[6]=4 不变继续循环 low++ 此时low的值为7  而high的值也为7
 *          此时退出循环
 *      9）、因为low和high的值相等，所以不再进行循环 将a[low]的值变为基数3 即a[7] = 3
 *      10）此时循环一遍的结果为：
 *          6, 4, 10, 9, 5, 7, 3, -2, 1, 2
 *      11）接下来只需要循环6, 4, 10, 9, 5, 7 和 -2, 1, 2这两部分即可。因为循环的逻辑参考上面的步骤。
 *      6）、7）、8）、9）、10）这几步只是上面的循环的重复。
 *
 */
public class QuickSortTest {

    /**
     * 快速排序
     */
    public static void main(String[] str){
        int[] a = {3,1,10,9,5,7,4,-2,6,2};
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a){

        quickSort(a,0,a.length-1);
    }

    public static void quickSort(int[] a,int low,int high){
        if(low < high){//退出递归的条件
            int position = pivot(a,low,high);//进行排序
            quickSort(a,low,position-1);//递归调用
            quickSort(a,position+1,high);//递归调用
        }
    }
    /**
     * 从大到小排序
     * 取下标的值
     * @param a 要排序的数组
     * @param low 数组中下标的最小值
     * @param high 数组中下标的最大值
     * @return
     */
    public static int pivot(int[] a,int low,int high){
        System.out.println(Arrays.toString(a));
        //基准数据
        int base = a[low];
        //这里要用while循环
        while (low < high){//这里要保证low < high
            //从右向左排序，如果a[high]小于基数，则继续循环，否则把high的数据赋值给low的数据
            while(a[high] < base && low < high){
                high--;//左边的
            }
            if(low < high){
                a[low] = a[high];
                low++; //low往前移一位
            }
            //如果a[low] 大于基数（base0），继续循环，否则把low数据赋值给high的数据
            while(a[low] > base && low < high){
                low++;
            }
            if(low < high){ //这个是必须的条件
                a[high] = a[low];
                high--;
            }
        }
        a[low] = base;//把基数赋值给low
        return low;
    }
}
