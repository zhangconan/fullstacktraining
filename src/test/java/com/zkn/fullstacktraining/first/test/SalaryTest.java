package com.zkn.fullstacktraining.first.test;

import com.zkn.fullstacktraining.first.QuickSortSalaryTest;
import com.zkn.fullstacktraining.first.RandomString;
import com.zkn.fullstacktraining.first.Salary;
import org.junit.Test;
import java.util.*;

/**
 * Created by zkn on 2016/12/3.
 */
public class SalaryTest {

    @Test
    public void testSalary(){
        /**
         * 输出收入最高的10个人的名单
         */
        List<Salary> salaryList = new ArrayList<>(10000);
        Random random = new Random();
        for(int i=0;i<10000;i++){
            Salary salary = new Salary();
            salary.setBaseSalary(random.nextInt(999996)+5);//随机范围是5-100万
            salary.setBonus(random.nextInt(100000));
            salary.setName(RandomString.getRamdomString(5));
            salaryList.add(salary);
        }
        List<Salary> salaryList1 = new ArrayList<>(salaryList);
        List<Salary> salaryList2 = new ArrayList<>(salaryList);
        Collections.sort(salaryList, new SalaryComparator());
        for(int i=0;i<10;i++){
            System.out.println(salaryList.get(i).toString());
        }
        System.out.println("开始冒泡排序了。。。。");
        testBubbleSort(salaryList1);
        System.out.println("开始快速排序了。。。。");
        QuickSortSalaryTest.quickSort(salaryList2);
    }
    /**
     * 冒泡排序
     * @param salaryList
     */
    private void testBubbleSort(List<Salary> salaryList) {
        //冒泡排序
        Salary[] salaries = (Salary[]) salaryList.toArray(new Salary[0]);
        List<Salary> salaryList1 = new ArrayList<>(10);
        for(int i=0;i<salaries.length-1;i++){
            //if(i > 9) //因为只要求取前10个，所以这里只需要排10次就可以了。
             //   break;
            for(int j=i;j<salaries.length;j++){
                Salary salary = new Salary();
                if(salaries[i].getResult() < salaries[j].getResult()){
                    swapSalary(salaries[i],salary);
                    swapSalary(salaries[j],salaries[i]);
                    swapSalary(salary,salaries[j]);
                }
            }
            salaryList1.add(salaries[i]);
        }
        for(int i=0;i<10;i++){
            System.out.println(salaryList1.get(i).toString());
        }
    }

    /**
     * 因为java中没有引用传递，所有的都是值传递。所以这里对两个对象进行重新赋值
     * @param src
     * @param obj
     */
    public void swapSalary(Salary src,Salary obj){
        obj.setBaseSalary(src.getBaseSalary());
        obj.setBonus(src.getBonus());
        obj.setName(src.getName());
    }

    @Test
    public void testRandomString(){
        System.out.println(RandomString.getRamdomString(5));
        System.out.println(RandomString.getRandomString01(5));
    }


    private static class SalaryComparator implements Comparator<Salary> {

        @Override
        public int compare(Salary o1, Salary o2) {

            return o2.getResult() - o1.getResult();
        }
    }
}
