package com.zkn.fullstacktraining.second;

import com.zkn.fullstacktraining.first.RandomString;
import com.zkn.fullstacktraining.first.Salary;
import com.zkn.fullstacktraining.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.function.Function;

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
        BufferedWriter bufferedWriter = null;
        LineNumberReader lnr = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\LearnVideo\\text.txt"),"utf-8")) ;
            for(int i=0;i<10000000;i++){
                bufferedWriter.write(salaries[i].getFileLine());//写入记录数据
                bufferedWriter.newLine();//换行
            }
            bufferedWriter.flush();
            lnr = new LineNumberReader(new InputStreamReader(new FileInputStream("D:\\LearnVideo\\text.txt"),"utf-8"));
            String str = null;
            Map<String,Map<Long,Integer>> totalMap = new HashMap<String,Map<Long,Integer>>();
            int i = 0;
            while(lnr.getLineNumber() >= i){
                i++;
                str = lnr.readLine();
                if(!StringUtils.isEmpty(str)){
                    String[] strs = str.split(",");
                    String tmpStr = strs[0].substring(0,2);
                    if(totalMap.get(tmpStr) != null){
                        Map<Long,Integer> oldMap = totalMap.get(tmpStr);
                        Map<Long,Integer> tmpMap = new HashMap<Long,Integer>();
                        for(Map.Entry<Long,Integer> entry : oldMap.entrySet()){
                            tmpMap.put(entry.getKey()+Long.parseLong(strs[1])*13+Long.parseLong(strs[2]),entry.getValue()+1);
                        }
                        oldMap = null;//让GC回收
                        totalMap.put(tmpStr,tmpMap);//放入新值
                    }else{
                        totalMap.put(tmpStr,new HashMap<Long,Integer>(){{put(Long.parseLong(strs[1])*13+Long.parseLong(strs[2]),1);}});
                    }
                }
            }
            Map<String,Long> tmpMap = new HashMap<String,Long>();
            for(Map.Entry<String,Map<Long,Integer>> entry : totalMap.entrySet()){
                String tmpStr = entry.getKey();
                Map<Long,Integer> map = entry.getValue();
                for(Map.Entry<Long,Integer> tmpEntry : map.entrySet()){
                    tmpMap.put(tmpStr,tmpEntry.getKey());
                }
            }
            List<Map.Entry<String,Long>> tmpList = new ArrayList<Map.Entry<String,Long>>(tmpMap.entrySet());
            Collections.sort(tmpList, new Comparator<Map.Entry<String, Long>>() {
                @Override
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {

                    return o1.getValue().longValue() > o2.getValue().longValue()? -1: 1;
                }
            });
            for(int j=0;j<10;j++){
                Map.Entry<String,Long> mapEntry = tmpList.get(j);
                System.out.println(mapEntry.getKey() +" , "+ (mapEntry.getValue()/10000)+" 万  " + totalMap.get(mapEntry.getKey()).get(mapEntry.getValue())+"个人");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
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
