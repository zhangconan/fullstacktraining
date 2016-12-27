package com.zkn.fullstacktraining.fourth;


import com.zkn.fullstacktraining.second.SalaryAssistScope;

import java.io.*;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by wb-zhangkenan on 2016/12/27.
 */
public class SalaryStream01 {

    public static void main(String[] args){
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
                        .entrySet().stream().sorted((int1,int2)-> Integer.compare(int1.getValue()[0],int2.getValue()[0]))
                        .limit(10)
                        .forEachOrdered(stringEntry -> System.out.println(stringEntry.getKey()+" "+stringEntry.getValue()[0]+" "+stringEntry.getValue()[1]));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
