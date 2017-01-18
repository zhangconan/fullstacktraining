package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zkn on 2017/1/17.
 */
public class ProducerAndConsumerModelLock02 {
    /**
     * 读写锁
     */
    public static Lock lock = new ReentrantLock();
    public static Condition consumerCondition = lock.newCondition();
    public static Condition producerCondition = lock.newCondition();
    /**
     * 共享变量
     */
    public static List<String> datas = new ArrayList<>();

    public static void main(String[] args){

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> {
            if (i % 2 == 0) {
                return new ConsumerLockThreadNew("consumer " + i);
            } else return new ProducerLockThreadNew("producer " + i);
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
class ConsumerLockThreadNew extends Thread {

    public ConsumerLockThreadNew(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (true){
            ProducerAndConsumerModelLock02.lock.lock();
            try{
                while (ProducerAndConsumerModelLock02
                        .datas.isEmpty()){
                    System.out.println(Thread.currentThread()
                            .getName() + " into wait ,because empty ");
                    try {
                        ProducerAndConsumerModelLock02
                                .consumerCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread()
                            .getName() + " wait finished ");
                }
                ProducerAndConsumerModelLock02
                        .datas.forEach(System.out::println);
                ProducerAndConsumerModelLock02.datas.clear();
                ProducerAndConsumerModelLock02
                        .producerCondition.signalAll();
            }finally {
                ProducerAndConsumerModelLock02
                        .lock.unlock();
            }
        }
    }
}

class ProducerLockThreadNew extends Thread {

    public ProducerLockThreadNew(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (true){
            ProducerAndConsumerModelLock02.lock.lock();
            try{
                while (ProducerAndConsumerModelLock02
                        .datas.size() > 1 ){
                    System.out.println(Thread.currentThread()
                            .getName() + " into wait,because full ");
                    try {
                        ProducerAndConsumerModelLock02
                                .producerCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread
                            .currentThread().getName() + "wait finished ");
                }
                IntStream.range(0, 1)
                        .forEach(i -> ProducerAndConsumerModelLock02.datas.add(i + " data"));
                ProducerAndConsumerModelLock02
                        .consumerCondition.signalAll();
            }finally {
                ProducerAndConsumerModelLock02
                       .lock.unlock();
            }
        }
    }
}

