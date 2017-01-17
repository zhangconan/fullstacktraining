package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by wb-zhangkenan on 2017/1/17.
 */
public class ProducerAndConsumerModelLock {
    //锁对象
    public static Lock lock = new ReentrantLock();
    //生产者产生的数据和消费者消费的数据
    public static List<String> datas = new ArrayList<>();
    //非空的条件
    public static Condition notEmpty = lock.newCondition();
    //非满的条件
    public static Condition notFull = lock.newCondition();

    public static void main(String[] args) {
        List<Thread> threadList = IntStream.range(1, 10)
                .mapToObj(i -> {
                    if (i % 2 == 0) {
                        return new ConsumerLockThread("消费者"+i);
                    }else{
                        return new ProducerLockThread("生产者"+i);
                    }
                })
                .filter(e->{e.start();return true;})
                .collect(Collectors.toList());
//        threadList.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
    }
}

class ProducerLockThread extends Thread {

    public ProducerLockThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (true) {
            ProducerAndConsumerModelLock.lock.lock();
            try {
                while (ProducerAndConsumerModelLock.datas.size() > 1) {
                    System.out.println(Thread.currentThread().getName() + " into wait,because full ");
                    try {
                        ProducerAndConsumerModelLock.notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "wait finished ");
                }
                IntStream.range(0, 1).forEach((i) -> ProducerAndConsumerModelLock.datas.add(i + " data lock的方式"));
                System.out.println("生产完"+Thread.currentThread().getName());
                //唤醒其他等待的线程
                ProducerAndConsumerModelLock.notFull.signalAll();
            } finally {
                System.out.println("生产者解锁了"+Thread.currentThread().getName());
                ProducerAndConsumerModelLock.lock.unlock();
            }
        }
    }
}

class ConsumerLockThread extends Thread {

    public ConsumerLockThread(String name) {
        this.setName(name);
    }
    @Override
    public void run() {
        while (true) {
            ProducerAndConsumerModelLock.lock.lock();
            try {
                while (ProducerAndConsumerModelLock.datas.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " into wait ,because empty ");
                    try {
                        ProducerAndConsumerModelLock.notFull.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " wait finished ");
                }
                ProducerAndConsumerModelLock.datas.forEach(System.out::println);
                System.out.println("消费完"+Thread.currentThread().getName());
                ProducerAndConsumerModelLock.datas.clear();
                ProducerAndConsumerModelLock.notEmpty.signalAll();
            } finally {
                System.out.println("消费者解锁了"+Thread.currentThread().getName());
                ProducerAndConsumerModelLock.lock.unlock();
            }
        }
    }
}
