package com.zkn.fullstacktraining.fourth;

import com.zkn.fullstacktraining.first.Salary;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class StreamCollectExercises01 {

    List<Salary> salaries = new ArrayList<Salary>() {
        {
            add(new Salary("zhansdgsan",15548,1555));
            add(new Salary("zwsha12ngsan",71,54));
            add(new Salary("zh323angsan",15002,15));
            add(new Salary("zsh323angsan",78455,15));
            add(new Salary("zh323angsan",145808,15));
            add(new Salary("zqh323angsan",882545,15));
        }
    };

    public static void main(String[] args) {

    }

    /**
     * 取平均值
     */
    @Test
    public void testCollect01(){
        Averager averageCollec = salaries.stream()
                .filter(salary -> salary.getBaseSalary()>1000)
                .map(salary -> salary.getBaseSalary())
                .collect(Averager::new,Averager::accepts,Averager::combines);
                //.collect(Salary::new,Averager::accept,new Averager().combine());
        System.out.println(averageCollec.average());
    }

    /**
     * 计算工资的总和
     */
    @Test
    public void testCollect02(){
        //计算每个人的每月工资总和
        salaries.stream().collect(Collectors.mapping(s->{int[] count = new int[1];count[0] += s.getBaseSalary();return count;},
                        Collectors.reducing((s1,s2)->{s1[0]+=s2[0];return s1;}))).ifPresent(s->System.out.println(s[0]));
    }

    @Test
    public void testPartitioningBy(){
        salaries.stream()
                .collect(Collectors.partitioningBy(e->e.getBaseSalary()>1500,
                Collectors.groupingBy(s->s.getName().substring(0,2))))
                .entrySet()
                .stream()
                .forEach(e->System.out.println(e.getKey()+"  "+e.getValue()));
    }

    @Test
    public void testCollect03(){
        //将流中的数据合并到第一个list上
        salaries.stream().reduce((s1,s2)->{
            s1.setBaseSalary(s1.getBaseSalary()+s2.getBaseSalary());
            s1.setBonus(s1.getBonus()+s2.getBonus());return s1;})
            .ifPresent(s->System.out.println(s.getName()+"  "
                    +s.getBaseSalary()+"  "+s.getBonus()));

        Salary salary =
                salaries.stream().reduce(new Salary("",0,0),(s1,s2)->{s1.setBaseSalary(s1.getBaseSalary()+s2.getBaseSalary());s1.setBonus(s1.getBonus()+s2.getBonus());return s1;});
        System.out.println(salary.getName()+"  "+salary.getBaseSalary()+"  "+salary.getBonus());
    }

    private static class SalaryTest {

        public static Salary apply(Salary s1,Salary s2){
            s1.setBaseSalary(s1.getBaseSalary()+s2.getBaseSalary());
            s1.setBonus(s2.getBonus()+s2.getBonus());
            return s1;
        }
        public static Salary applys(){

            return new Salary();
        }
    }

    @Test
    public void testAverager(){

        salaries.stream().max((s1,s2) -> Integer.compare(s1.getBaseSalary(),s2.getBaseSalary())).ifPresent(System.out::println);
    }

    public static class Averager {
        private int total;
        private int count;
        /**
         * Performs this operation on the given argument.
         *
         * @param value the input argument
         */
       // @Override
        public void accepts(int value) {
            total += value;
            count++;
        }
        public double average() {

            return count > 0 ? ((double) total)/count : 0;
        }
        public void combines(Averager other) {
            total += other.total;
            count += other.count;
        }
    }
}
