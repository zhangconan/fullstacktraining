package com.zkn.fullstacktraining.seventh;

import javafx.util.Pair;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;
/**
 * Created by zkn on 2017/2/5.
 */
public class SearchStringByThreadPool {

    public static void main(String[] args) {

        try {
            //创建5个固定线程的线程池
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            List<Future<Pair<String, Integer>>> listFile =
                    //这里是取所传入目录的最多四层，如果不知道这个API的话需要递归去做。
                    Files.walk(Paths.get("D:\\CUST\\WORK\\Exercises\\FullStackTraining\\src\\main\\java\\com\\zkn"), 4)
                            .filter(file -> !Files.isDirectory(file) && file.toString().endsWith("java"))//文件文件夹和不是java的文件
                            .map(file -> (Callable<Pair<String, Integer>>) () -> {//创建N多个Callable实现类
                                Pair<String, Integer> pair = null;//这里的键值对用pair比用Map更好一些
                                try {
                                    Optional optional = Files.lines(file).map(str -> {
                                        int count = 0;
                                        int index = str.indexOf("main");
                                        if (index >= 0) {
                                            //这里需要循环计算，因为可能在某一行中会出现多次
                                            do {
                                                count++;
                                            } while ((index = str.indexOf("main", index + 1)) > 0);
                                        }
                                        return count;
                                    }).reduce(Integer::sum);//合并最终的计算结果
                                    int count = optional.isPresent() ? (int) optional.get() :0;
                                    pair = new Pair<>(file.toString(),count);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return pair == null ? new Pair<>("", 0) : pair;
                            })
                            .map(file -> executorService.submit(file))//提交给线程池进行处理
                            .collect(Collectors.toList());
            listFile.stream().map(file -> {
                Pair<String, Integer> pair = null;
                try {
                    pair = file.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return pair == null ? new Pair<>("", 0) : pair;
            })
                    .filter(file -> file.getValue() > 0)//过滤掉不包含字符串的文件
                    .sorted((s1, s2) -> Integer.compare(s2.getValue(), s1.getValue()))//从大到小排序
                    .forEach(file -> System.out.println(String.format("%d次出现在%s文件中", file.getValue(), file.getKey())));
            //关闭线程池
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        String str = "mainmainmainmainmain";
    }
}