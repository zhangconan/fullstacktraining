package com.zkn.fullstacktraining.third;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zkn on 2016/12/24.
 */
public class AnalyzeIdentity {

    public static void main(String[] args){
        Map<Integer,String> map = new IdentityHashMap<>();
        Integer a=5;
        Integer b=5;
        map.put(a,"100");
        map.put(b,"100");
        System.out.println(map.size());
        map.clear();
        a = Integer.MAX_VALUE-1;
        b = Integer.MAX_VALUE-1;
        map.put(a,"100");
        map.put(b,"100");
        System.out.println(map.size());
    }
}
