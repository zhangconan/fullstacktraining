package com.zkn.fullstacktraining.fourth;

import com.zkn.fullstacktraining.first.Salary;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class StreamCollectExercises01 {

    List<Salary> salaries = new ArrayList<Salary>() {
        {
            add(new Salary("zhansdgsan",15548,1555));
            add(new Salary("zhawe2ngsan",784540,15575));
            add(new Salary("zhawwngsan",15548,55));
            add(new Salary("zwehangsan",54472,974));
            add(new Salary("zhaw1ngsan",1204,784));
            add(new Salary("zha12ngsan",71,754));
            add(new Salary("zh323angsan",78,15));
            add(new Salary("zhawengsan",4199,74288));
        }
    };

    public static void main(String[] args) {

    }

    @Test
    public void testCollect01(){

        Averager averageCollec = salaries.stream()
                .filter(salary -> salary.getBaseSalary()>1000)
                .map(salary -> salary.getBaseSalary())
                .collect(Averager::new,Averager::accepts,Averager::combines);
                //.collect(Salary::new,Averager::accept,new Averager().combine());
        System.out.println(averageCollec.average());
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
