package com.zkn.fullstacktraining.first.test;

import com.zkn.fullstacktraining.first.Salary;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wb-zhangkenan on 2016/12/16.
 */
public class SalaryGroupingTest {

    @Test
    public void testGrouping(){
        List<Salary> list = new ArrayList<Salary>(){
            {
                add(new Salary("zhangsan",100,20));
                add(new Salary("zsangsan",100,20));
                add(new Salary("z4angsan",100,20));
                add(new Salary("zqangsan",100,20));
                add(new Salary("zrangsan",100,20));
                add(new Salary("zhangsan",100,20));
                add(new Salary("zsangsan",100,20));
                add(new Salary("zyangsan",100,20));
            }
        };
    }
}
