package com.zkn.fullstacktraining.third;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by zkn on 2016/12/20.
 */
public class TreeSetTest {

    @Test
    public void testSetNull(){
        Set<String> set = new TreeSet<String>();
        set.add("zhangsan");
        System.out.println(Arrays.toString(set.toArray()));
    }
}
