package com.zkn.fullstacktraining.third;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wb-zhangkenan on 2016/12/23.
 */
public class HashTableTest {

    public static void main(String[] args){

        Map<String,String> map = new Hashtable<String,String>(){
            {
                put("zhang","lisi");
                put("zhang01","lisi01");
                put("zhang02","lisi02");
                put("zhang03","lisi03");
                put("zhang04","lisi04");
            }
        };

        for(Map.Entry<String,String> entry : map.entrySet()){

        }

        Thread thread01 = new Thread(new Runnable(){

            @Override
            public void run() {
                map.put("zhang11","sdwewe");
            }
        });

        Thread thread02 = new Thread(new Runnable(){

            @Override
            public void run() {
                for(Iterator iterator=map.entrySet().iterator(); iterator.hasNext();){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Map.Entry<String,String> entry = (Map.Entry<String, String>) iterator.next();
                    System.out.println(entry.getKey());
                    if("zhang".equals(entry.getKey())){
                        iterator.remove();
                    }
                }
            }
        });
        thread02.start();
        thread01.start();
    }
}
