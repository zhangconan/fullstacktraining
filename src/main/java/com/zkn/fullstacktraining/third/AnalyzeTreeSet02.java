package com.zkn.fullstacktraining.third;

import com.zkn.fullstacktraining.first.Salary;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wb-zhangkenan on 2016/12/23.
 * TreeSet里的自定义对象必须要实现什么方法，说明原因
 */
public class AnalyzeTreeSet02 {

    /**
     * TreeSet放入自定义对象需要实现hashCode和equals、compareTo方法。
     * 因为TreeSet中的元素都是有序的，所以
     * TreeSet中的元素都必须实现Comparable接口（或者被指定的比较器所接受）。另外，所有这些元素都必须是可互相比较的。
     */
    public static void main(String[] args){
        Set<Salary> salarySet = new TreeSet<>();
        Salary salary = new Salary();
        salary.setName("zhangsan");
        salarySet.add(salary);
        Salary salary01 = new Salary();
        salary01.setName("zhangsan");
        salarySet.add(salary01);
        Salary salary02 = new Salary();
        salary02.setName("zhangsan");
        salarySet.add(salary02);

        System.out.println(salarySet.toArray(new Salary[0]));
    }
}
