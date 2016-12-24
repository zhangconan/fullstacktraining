package com.zkn.fullstacktraining.third;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zkn on 2016/12/24.
 */
public class AnalyzeLinkedHashMap {

    public static void main(String[] args){

        Map<String,String> linkedHashMap = new LinkedHashMap<>();
        Map<String,String> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            linkedHashMap.put(i+"",i+"");
        }
        System.out.println("LinkedHashMap耗时："+(System.currentTimeMillis()-startTime));
        startTime = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            hashMap.put(i+"",i+"");
        }
        System.out.println("hashMap："+(System.currentTimeMillis()-startTime));

        startTime = System.currentTimeMillis();
        System.out.println(linkedHashMap.get("999999"));
        System.out.println("linkedHashMap："+(System.currentTimeMillis()-startTime));

        startTime = System.currentTimeMillis();
        System.out.println(hashMap.get("999999"));
        System.out.println("hashMap："+(System.currentTimeMillis()-startTime));
    }
}
