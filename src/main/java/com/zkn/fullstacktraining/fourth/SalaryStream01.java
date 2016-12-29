package com.zkn.fullstacktraining.fourth;


import com.zkn.fullstacktraining.second.SalaryAssistScope;
import org.junit.Test;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by wb-zhangkenan on 2016/12/27.
 */
public class SalaryStream01 {

    public static void main(String[] args){

    }
    @Test
    public void thirdExample(){
        LineNumberReader lnReader = null;
        try {
            lnReader = new LineNumberReader(new InputStreamReader(
                    new FileInputStream("D:\\LearnVideo\\text.txt"),"utf-8"));
            lnReader.lines().parallel()
                    .map(str -> {String[] strs = str.split(",");//对单个元素进行处理
                        return new SalaryAssistScope(strs[0].substring(0,2),Long.parseLong(strs[1])*13+Long.parseLong(strs[2]),1);})
                    .filter(salary->salary.getSalaryTotal()>100000)//过滤掉不符合条件的数据
                    .collect(Collectors.groupingBy(salary->salary.getName()))
                    .entrySet()
                    .stream().parallel()
                    .map(str->function.apply(str.getValue()))
                    .sorted((s1,s2)->Long.compare(s2.getSalaryTotal(),s1.getSalaryTotal()))
                    .limit(10)
                    .forEachOrdered(salaryAssistScope -> System.out.println(salaryAssistScope));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    Function<List<SalaryAssistScope>,SalaryAssistScope> function = salaryFunction->{
        return salaryFunction.stream().collect(Collectors.reducing(
                new SalaryAssistScope("",0L,0),
                (s1,s2)->{s1.setName(s2.getName());s1.setSalaryTotal(s1.getSalaryTotal()+s2.getSalaryTotal());s1.setCount(s1.getCount()+s2.getCount());return s1;}));
    };
    @Test
    public void secondExample(){
        LineNumberReader lnReader = null;
        try {
            lnReader = new LineNumberReader(new InputStreamReader(
                    new FileInputStream("D:\\LearnVideo\\text.txt"),"utf-8"));
            lnReader.lines()//得到Stream流
                    .map(str -> salaryFunction.apply(str))//map对单个元素进行处理，自定义Function，返回SalaryAssistScope对象
                    .filter(salaryAssistScope -> predicate.test(salaryAssistScope))//过滤掉不符合条件的数据
                    .collect(Collectors.groupingBy(str->str.getName(),      //根据姓名分组
                            Collector.of(()->new SalaryAssistScope("",0L,0), //Supplier 创建一个对象
                                    (salary, salaries) -> {
                                            salary.setSalaryTotal(salary.getSalaryTotal()+salaries.getSalaryTotal());
                                            salary.setCount(salary.getCount()+1);},   // BiConsumer<A,T> 往A中添加T元素
                                    (salary,salaries)->{
                                        salary.setSalaryTotal(salary.getSalaryTotal()+salaries.getSalaryTotal());
                                        salary.setCount(salary.getCount()+1);
                                        return salary; //BinaryOperator<A> 把多个A合并成一个
                                    })))
                    .entrySet() //得到Map中的Map.Entry<String,SalaryAssistScope>
                    .stream()   //得到Entry流
                    .parallel() //并行
                    .sorted((first,second) -> Long.compare(second.getValue().getSalaryTotal(),first.getValue().getSalaryTotal()))//排序
                    .limit(10)//取前10个
                    .forEachOrdered(salary -> System.out.println("姓名："+salary.getKey()+" "+salary.getValue().getSalaryTotal()+" 万" + salary.getValue().getCount()+" 人"));//输出
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

    @Test
    public void firstExample() {
        LineNumberReader lnReader = null;
        try {
            lnReader = new LineNumberReader(new InputStreamReader(
                    new FileInputStream("D:\\LearnVideo\\text.txt"),"utf-8"));
            lnReader.lines().map(s -> new SalaryAssistScope(s.split(",")[0].substring(0,2),Long.parseLong(s.split(",")[1])*13+Long.parseLong(s.split(",")[2]),1))
                        .filter(salary -> salary.getSalaryTotal() > 100000)
                        .collect(Collectors.groupingBy(
                                salary->salary.getName(),
                                Collector.of(()->new int[2],
                                        (ints, salary) -> {ints[0] += salary.getSalaryTotal();ints[1] += 1;},
                                        (ints1, ints2) -> {ints1[0] += ints2[0];ints1[1] += ints2[1];return ints1;})))
                        .entrySet().stream().sorted((int1,int2)-> Integer.compare(int2.getValue()[0],int1.getValue()[0]))
                        .limit(10)
                        .forEachOrdered(stringEntry -> System.out.println(stringEntry.getKey()+" "+stringEntry.getValue()[0]+" "+stringEntry.getValue()[1]));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (lnReader != null)
                try {
                    lnReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
