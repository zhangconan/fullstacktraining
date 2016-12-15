package com.zkn.fullstacktraining.second;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by wb-zhangkenan on 2016/12/13.
 */
public class SalaryFile02 {

    public static void main(String[] args){
        //安行读取文件
        LineNumberReader lnr = null;
        try{
            lnr = new LineNumberReader(new InputStreamReader(new FileInputStream("G:\\LearnVideo\\text.txt"),"utf-8"));
            String str = null;
            Map<String,SalaryAssistScope> salaryMap = new HashMap<String,SalaryAssistScope>();
            while((str=lnr.readLine()) != null){
                String[] tmpStrArr = str.split(",");
                String tmpStr = tmpStrArr[0].substring(0,2);
                if(salaryMap.get(tmpStr) != null){
                    SalaryAssistScope temSalary = salaryMap.get(tmpStr);
                    temSalary.setSalaryTotal(temSalary.getSalaryTotal()+Long.parseLong(tmpStrArr[1])*13+Long.parseLong(tmpStrArr[2]));
                    temSalary.setCount(temSalary.getCount()+1);
                }else{
                    salaryMap.put(tmpStr,new SalaryAssistScope(tmpStr,
                            Long.parseLong(tmpStrArr[1])*13+Long.parseLong(tmpStrArr[2]),1));
                }
            }
            PriorityQueue<SalaryAssistScope> priorityQueue = new PriorityQueue<SalaryAssistScope>(10);
            for(Map.Entry<String,SalaryAssistScope> entry : salaryMap.entrySet()){
                priorityQueue.add(entry.getValue());
            }
            for(int i=0;i<10;i++){
                System.out.println(priorityQueue.poll());
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            if(lnr != null)
                try {
                    lnr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
