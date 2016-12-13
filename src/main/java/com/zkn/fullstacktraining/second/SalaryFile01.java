package com.zkn.fullstacktraining.second;

import com.zkn.fullstacktraining.first.RandomString;
import com.zkn.fullstacktraining.first.Salary;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * Created by zkn on 2016/12/12.
 * 随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
 *    然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，最后做排序和分组，输出年薪总额最高的10组：
 *   wx, 200万，10人
 *   lt, 180万，8人
 */
public class SalaryFile01 {

    private static Random random = new Random();

    public static void main(String[] args){
        Salary[] salaries = getSalary();
        OutputStreamWriter osw = null;
        LineNumberReader lnr = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream("G:\\LearnVideo\\text.txt"),"utf-8");
            for(int i=0;i<salaries.length;i++){
                osw.write(salaries[i].getFileLine());
                osw.write("\r");
            }
            lnr = new LineNumberReader(new InputStreamReader(new FileInputStream("G:\\LearnVideo\\text.txt"),"utf-8"));
            String str = null;
            lnr.lines().filter(line -> line == "" ).map(line -> line.split(",")).
                    map((str1) -> (str1[0].substring(0,2)+" , "+(Integer.parseInt(str1[1])+Integer.parseInt(str1[2])))).
                    sorted().limit(20).forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(osw != null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Salary[] getSalary(){
        Salary[] salaries = new Salary[10000000];
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
