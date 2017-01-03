package com.zkn.fullstacktraining.fourth;

import com.zkn.fullstacktraining.second.SalaryAssistScope;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by zkn on 2017/1/3.
 */
public class SalaryStream03 {

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
                    .collect(new CollectorsImpl())
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

    static class CollectorsImpl implements Collector<SalaryAssistScope,
            Map<String,SalaryAssistScope>, Map<String,SalaryAssistScope>> {

        @Override
        public Supplier<Map<String, SalaryAssistScope>> supplier() {

            return HashMap<String, SalaryAssistScope>::new;
        }

        @Override
        public BiConsumer<Map<String, SalaryAssistScope>, SalaryAssistScope> accumulator() {

            return (e1,e2)->{
                SalaryAssistScope salaryAssistScope = e1.get(e2.getName());
                if(salaryAssistScope == null){
                    e1.put(e2.getName(),e2);
                }else{
                    salaryAssistScope.setSalaryTotal(salaryAssistScope.getSalaryTotal()+e2.getSalaryTotal());
                    salaryAssistScope.setCount(salaryAssistScope.getCount()+1);
                    e1.put(e2.getName(),salaryAssistScope);
                }
            };
        }

        @Override
        public BinaryOperator<Map<String, SalaryAssistScope>> combiner() {

            return (e1,e2)->{
                e1.putAll(e2);
                return e1;
            };
        }

        @Override
        public Function<Map<String, SalaryAssistScope>, Map<String, SalaryAssistScope>> finisher() {

            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }
}
