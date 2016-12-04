package com.zkn.fullstacktraining.first;

import java.util.List;

/**
 * Created by zkn on 2016/12/4.
 */
public class QuickSortSalaryTest {

    public static void quickSort(List<Salary> salaryList){
        if(salaryList == null || salaryList.isEmpty() )
            return;
        Salary[] salaries = salaryList.toArray(new Salary[0]);
        recursion(salaries,0,salaryList.size()-1);
        for(int i=0;i<10;i++){
            System.out.println(salaries[i].toString());
        }
    }
    /*
    这里要递归调用排序方法
     */
    public static void recursion(Salary[] salaries,int low,int high){
        if(low < high){
            int position = pivot(salaries,low,high);
            recursion(salaries,low,position-1);
            recursion(salaries,position+1,high);
        }
    }
    /*
        排序，并取出基准元素的位置
     */
    public static int pivot(Salary[] salaries,int low,int high){
        //先选中一个基准元素
        Salary salary = new Salary();
        swapSalary(salaries[low],salary);
        //要把数组中的元素分成两部分，左边的部分比基准元素中的result大，右边的部分比基准元素中的result小
        while (low < high){
            //这里一定要确保基准元素左右两边的数据不能产生交叉，所以low一定要小于high
            //如果右边的result比基准元素中的result小，则满足循环条件，否则则交换
            while(salaries[high] .getResult() < salary.getResult() && low < high){
                high--;
            }
            if (low < high){
                //这时说明右边的result比基准元素中的result大
                swapSalary(salaries[high],salaries[low]);
                //左边的元素向右移一位
                low++;
            }
            //如果左边的result比基准元素中的result大，则满足循环条件，否则则交换
            while(salaries[low].getResult() > salary.getResult() && low < high){
                low++;
            }
            if(low < high){
                swapSalary(salaries[low],salaries[high]);
                high--;
            }
        }
        swapSalary(salaries[low],salary);
        return low;
    }

    public static void swapSalary(Salary src,Salary desc){
        desc.setName(src.getName());
        desc.setBonus(src.getBonus());
        desc.setBaseSalary(src.getBaseSalary());
    }
}
