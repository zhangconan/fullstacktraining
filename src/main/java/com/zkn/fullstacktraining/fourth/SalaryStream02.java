package com.zkn.fullstacktraining.fourth;

import com.zkn.fullstacktraining.first.Salary;
import com.zkn.fullstacktraining.second.SalaryAssistScope;
import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by zkn on 2017/1/3.
 */
public class SalaryStream02 {

    @Test
    public void secondExample(){
        LineNumberReader lnReader = null;
        try {
            long startTime = System.currentTimeMillis();
            lnReader = new LineNumberReader(new InputStreamReader(
                    new FileInputStream("D:\\LearnVideo\\text.txt"),"utf-8"));
            lnReader.lines()//得到Stream流
                    .map(str -> salaryFunction.apply(str))//map对单个元素进行处理，自定义Function，返回SalaryAssistScope对象
                    .filter(salaryAssistScope -> predicate.test(salaryAssistScope))//过滤掉不符合条件的数据
                    .collect(Collectors.groupingBy(str->str.getName(),      //根据姓名分组
                            new CollectorsImpl()))
                    .entrySet()//得到Map中的Map.Entry<String,SalaryAssistScope>
                    .stream()   //得到Entry流
//                    //.parallel() //并行
                    .sorted((first,second) -> Long.compare(second.getValue().getSalaryTotal(),first.getValue().getSalaryTotal()))//排序
                    .limit(10)//取前10个
                    .forEachOrdered(salary -> System.out.println("姓名："+salary.getKey()+" "+salary.getValue().getSalaryTotal()/10000+" 万"
                            + salary.getValue().getCount()+" 人"));//输出
            System.out.println("并行耗时："+(System.currentTimeMillis()-startTime));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(lnReader != null)
                try {
                    lnReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    Function<String,SalaryAssistScope> salaryFunction = s -> {
        String[] strs = s.split(",");
        String str = strs[0].substring(0,2);
        Long total = Long.parseLong(strs[1])*13 + Long.parseLong(strs[2]);
        int count = 1;
        return new SalaryAssistScope(str,total,count);
    };

    Predicate<? super SalaryAssistScope> predicate =
            salaryAssistScope -> salaryAssistScope.getSalaryTotal() > 100000;

    static class CollectorsImpl implements Collector<SalaryAssistScope, SalaryAssistScope, SalaryAssistScope> {

        @Override
        public Supplier<SalaryAssistScope> supplier() {
            return SalaryAssistScope::new;
        }

        @Override
        public BiConsumer<SalaryAssistScope, SalaryAssistScope> accumulator() {
            return (e1,e2)->{e1.setSalaryTotal(e1.getSalaryTotal()+e2.getSalaryTotal());
                e1.setCount(e1.getCount()+1);};
        }

        @Override
        public BinaryOperator<SalaryAssistScope> combiner() {
            return (e1,e2)->{e1.setSalaryTotal(e1.getSalaryTotal()+e2.getSalaryTotal());e1.setCount(e1.getCount()+1); return e1;};
        }

        @Override
        public Function<SalaryAssistScope, SalaryAssistScope> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }

    static class Com{
        private SalaryAssistScope salaryScope = new SalaryAssistScope("",0L,0);

        public SalaryAssistScope getSalaryScope() {
            return salaryScope;
        }

        public SalaryAssistScope accept(SalaryAssistScope salary){
            salaryScope.setSalaryTotal(salaryScope.getSalaryTotal()+salary.getSalaryTotal());
            salaryScope.setCount(salaryScope.getCount()+1);
            return salaryScope;
        }

        public SalaryAssistScope cobmines(Com com){
            this.getSalaryScope().setSalaryTotal(salaryScope.getSalaryTotal()+com.getSalaryScope().getSalaryTotal());
            this.getSalaryScope().setCount(salaryScope.getCount()+com.getSalaryScope().getCount());
            return this.getSalaryScope();
        }
    }
}
