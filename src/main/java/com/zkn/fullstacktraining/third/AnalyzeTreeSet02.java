package com.zkn.fullstacktraining.third;

import com.zkn.fullstacktraining.first.Salary;
import java.util.*;

/**
 * Created by wb-zhangkenan on 2016/12/23.
 * TreeSet里的自定义对象必须要实现什么方法，说明原因
 */
public class AnalyzeTreeSet02 {

    /**
     * TreeSet放入自定义对象需要实现hashCode和equals、compareTo方法。
     * TreeSet的底层是用TreeMap实现的。因为TreeSet中的元素都是有序的，所以TreeSet中的元素要么实现Comparable接口要么被指定的比较器所接受。
     * 所有这些元素都必须是可互相比较的。
     *      final int compare(Object k1, Object k2) {
     *           return comparator==null ? ((Comparable<? super K>)k1).compareTo((K)k2)
     *           : comparator.compare((K)k1, (K)k2);
     *       }
     * 所以，如果你在构造函数时没有指定比较器，则需要实现Comparable接口，实现compareTo方法。
     *      如果指定的比较器没有和你的自定义类没有关系的话,则结果可能会不符合预期，或者编译不通过。
     */
    public static void main(String[] args){
        Set<Salary> salarySet = new TreeSet<>();
        Salary salary = new Salary();
        salary.setName("zhangsan");
        salarySet.add(salary);
        Salary salary01 = new Salary();
        salary01.setName("zhangsan01");
        salarySet.add(salary01);
        Salary salary02 = new Salary();
        salary02.setName("zhangsan001");
        salarySet.add(salary02);
        System.out.println(Arrays.toString(salarySet.toArray(new Salary[0])));
    }
    private static class SalaryComparator implements Comparator<PersonDomain>{

        @Override
        public int compare(PersonDomain o1, PersonDomain o2) {

            return o1.getName().length() > o2.getName().length() ? 1 : -1;
        }
    }
}
