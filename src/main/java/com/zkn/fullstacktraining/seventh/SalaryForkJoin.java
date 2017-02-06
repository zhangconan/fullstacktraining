package com.zkn.fullstacktraining.seventh;

import com.zkn.fullstacktraining.second.SalaryAssistScope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zkn on 2017/2/6.
 */
public class SalaryForkJoin extends RecursiveTask<Map<String, SalaryAssistScope>> {
    //任务的阀值
    private static final int THRESHOLD = 20;
    //任务区间的开始值
    private int start;
    //任务区间的结束值
    private int end;
    //要计算的数据
    private List<String> stringList;

    public SalaryForkJoin(int start, int end, List<String> stringList) {
        this.start = start;
        this.end = end;
        this.stringList = stringList;
    }

    public static void main(String[] args) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("D:\\LearnVideo\\text.txt"));
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            SalaryForkJoin salaryForkJoin = new SalaryForkJoin(0, allLines.size(), allLines);
            Future<Map<String, SalaryAssistScope>> listResult = forkJoinPool.submit(salaryForkJoin);
            Map<String, SalaryAssistScope> mapList = listResult.get();
            mapList.entrySet()
                    .stream()
                    .sorted((s1, s2) -> Long.compare(s2.getValue().getSalaryTotal(), s1.getValue().getSalaryTotal()))
                    .limit(10)
                    .forEach(s1 -> System.out.println(s1.getValue()));
            forkJoinPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, SalaryAssistScope> compute() {

        Map<String, SalaryAssistScope> mapList;
        boolean canCompute = end - start <= THRESHOLD;
        if (canCompute) {
            mapList = IntStream.range(start, end + 1)
                    .filter(i -> i < stringList.size())
                    .mapToObj(i -> {
                        String[] strings = stringList.get(i).split(",");
                        return new SalaryAssistScope(strings[0].substring(0, 2),
                                Long.parseLong(strings[1]) * 13 + Long.parseLong(strings[2]), 1);
                    }).collect(Collectors.groupingBy(SalaryAssistScope::getName,
                            Collector.of(SalaryAssistScope::new,
                                    (s1, s2) -> {
                                        s1.setSalaryTotal(s1.getSalaryTotal() + s2.getSalaryTotal());
                                        s1.setCount(s1.getCount() + 1);
                                        s1.setName(s2.getName());
                                    },
                                    (s1, s2) -> {
                                        s1.setSalaryTotal(s1.getSalaryTotal() + s2.getSalaryTotal());
                                        s1.setCount(s1.getCount() + 1);
                                        s1.setName(s2.getName());
                                        return s1;
                                    })));
        } else {
            int middle = (end + start) / 2;
            //创建Fork任务
            SalaryForkJoin leftForkJoin = new SalaryForkJoin(start, middle, stringList);
            SalaryForkJoin rightForkJoin = new SalaryForkJoin(middle + 1, end, stringList);
            invokeAll();
            //执行子任务
            leftForkJoin.fork();
            rightForkJoin.fork();
            mapList = reduceResult(leftForkJoin.join(), rightForkJoin.join());
        }
        return mapList;
    }

    private Map<String, SalaryAssistScope> reduceResult(Map<String, SalaryAssistScope> m1,
                                                        Map<String, SalaryAssistScope> m2) {
        if (m1 != null && m2 != null) {
            m2.entrySet().stream().forEach(map -> m1.merge(map.getKey(), map.getValue(), (k, v) -> {
                k.setSalaryTotal(k.getSalaryTotal() + v.getSalaryTotal());
                k.setCount(k.getCount() + v.getCount());
                return k;
            }));
            return m1;
        }
        return m1;
    }
}
