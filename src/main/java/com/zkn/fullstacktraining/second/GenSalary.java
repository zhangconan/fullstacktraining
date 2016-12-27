package com.zkn.fullstacktraining.second;

import com.zkn.fullstacktraining.first.RandomString;
import com.zkn.fullstacktraining.first.Salary;

import java.util.Random;

/**
 * Created by zkn on 2016/12/15.
 */
public class GenSalary {

    private static Random random = new Random();

    public static Salary[] getSalary(){
        Salary[] salaries = new Salary[1000];
        for(int i=0;i<salaries.length;i++){
            Salary salary = new Salary();
            salary.setName(RandomString.getRamdomString(5));
            salary.setBaseSalary(random.nextInt(10000000));
            salary.setBonus(random.nextInt(10000000));
            salaries[i] = salary;
        }
        return salaries;
    }
}
