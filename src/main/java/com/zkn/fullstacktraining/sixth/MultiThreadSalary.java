package com.zkn.fullstacktraining.sixth;

import com.zkn.fullstacktraining.second.SalaryAssistScope;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zkn on 2017/1/17.
 */
public class MultiThreadSalary {
    //创建一个存放8个元素的集合
    List<Map<String, SalaryAssistScope>> salaryCollection = new ArrayList<>(8);

    public static void main(String[] args) {
        List<String> stringList = readFile();
        int size = stringList.size();
        int average = size / 8;
        MultiThreadSalary multiThreadSalary = new MultiThreadSalary();
        List<Thread> threadList = IntStream.range(0, 8)
                .mapToObj((i) -> {
                            System.out.println(i);
                            if (i == 7) {
                                return new Thread(() ->
                                        multiThreadSalary.reserveSalary(stringList, average * i, size));
                            } else {
                                return new Thread(() ->
                                        multiThreadSalary.reserveSalary(stringList, average * i, average * i + average));
                            }
                        }
                )
                .filter(e -> {
                    e.start();
                    return true;
                }).collect(Collectors.toList());
        threadList.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        multiThreadSalary.salaryCollection
                .stream()
                .reduce((map1, map2) -> {
                    map2.entrySet().stream().forEach(map ->
                            map1.merge(map.getKey(), map.getValue(), (k, v) -> {
                        k.setSalaryTotal(k.getSalaryTotal() + v.getSalaryTotal());
                        k.setCount(k.getCount() + v.getCount());
                        return k;
                    }));
                    return map1;
                }).ifPresent(s -> s.entrySet().stream()
                .sorted((first, second) ->
                        Long.compare(second.getValue().getSalaryTotal(),
                                first.getValue().getSalaryTotal()))//排序
                .limit(10)//取前10个
                .forEachOrdered(salary ->
                        System.out.println("姓名：" +
                                salary.getKey() + " " +
                                salary.getValue().getSalaryTotal() / 10000 + " 万"
                        + salary.getValue().getCount() + " 人")));

    }

    public void reserveSalary(List<String> salaries, long start, long end) {
        salaryCollection.add(salaries.stream()
                .skip(start)//跳过前面的
                .limit(end - start)
                .map(str -> {
                    String[] strs = str.split(",");//对单个元素进行处理
                    return new SalaryAssistScope(strs[0].substring(0, 2),
                            Long.parseLong(strs[1]) * 13 + Long.parseLong(strs[2]), 1);
                }).collect(Collectors.groupingBy(str -> str.getName(),
                        Collector.of(() -> new SalaryAssistScope(),
                                (s1, s2) -> {
                                    s1.setSalaryTotal(s1.getSalaryTotal()
                                            + s2.getSalaryTotal());
                                    s1.setName(s2.getName());
                                    s1.setCount(s1.getCount() + 1);
                                },
                                (s1, s2) -> {
                                    s1.setSalaryTotal(s1.getSalaryTotal()
                                            + s2.getSalaryTotal());
                                    s1.setName(s2.getName());
                                    s1.setCount(s1.getCount() + 1);
                                    return s1;
                                }))));
    }

    public static List<String> readFile() {
        try {
            return Files.readAllLines(Paths.get("D:\\LearnVideo\\text.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
