package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wb-zhangkenan on 2017/1/17.
 */
public class ProducerAndConsumerModelReadWriterLock {

    public static ReadWriteLock readWritelock = new ReentrantReadWriteLock();

    public static List<String> datas = new ArrayList<>();
}
