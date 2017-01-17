package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zkn on 2017/1/17.
 */
public class ProducerAndConsumerModelReadWriterLock {
    /**
     * 读写锁
     */
    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static Condition readCondition = readWriteLock.writeLock().newCondition();

    public static Condition writeCondition = readWriteLock.writeLock().newCondition();
    /**
     * 共享变量
     */
    public static List<String> datas = new ArrayList<>();

    public static void main(String[] args){

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> {
            if (i % 2 == 0) {
                return new ReadLockThread("consumer " + i);
            } else return new WriteLockThread("producer " + i);
        }).filter(t -> {
            t.start();
            return true;
        }).collect(Collectors.toList());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });
    }
}

class ReadLockThread extends Thread {

    public ReadLockThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (true){
            ProducerAndConsumerModelReadWriterLock.readWriteLock.writeLock().lock();
            try{
                while (ProducerAndConsumerModelReadWriterLock.datas.isEmpty()){
                    System.out.println(Thread.currentThread().getName() + " into wait ,because empty ");
                    try {
                        ProducerAndConsumerModelReadWriterLock.readCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " wait finished ");
                }
                ProducerAndConsumerModelReadWriterLock.datas.forEach(System.out::println);
                ProducerAndConsumerModelReadWriterLock.datas.clear();
                ProducerAndConsumerModelReadWriterLock.writeCondition.signalAll();
            }finally {
                ProducerAndConsumerModelReadWriterLock.readWriteLock.writeLock().unlock();
            }
        }
    }
}

class WriteLockThread extends Thread {

    public WriteLockThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (true){
            ProducerAndConsumerModelReadWriterLock.readWriteLock.writeLock().lock();
            try{
                while (ProducerAndConsumerModelReadWriterLock.datas.size() > 1 ){
                    System.out.println(Thread.currentThread().getName() + " into wait,because full ");
                    try {
                        ProducerAndConsumerModelReadWriterLock.writeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "wait finished ");
                }
                IntStream.range(0, 1).forEach(i -> ProducerAndConsumerModel03.datas.add(i + " data"));
                ProducerAndConsumerModelReadWriterLock.readCondition.signalAll();
            }finally {
                ProducerAndConsumerModelReadWriterLock.readWriteLock.writeLock().unlock();
            }
        }
    }
}
