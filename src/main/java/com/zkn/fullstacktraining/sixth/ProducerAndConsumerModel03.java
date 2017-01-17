package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by wb-zhangkenan on 2017/1/17.
 */
public class ProducerAndConsumerModel03 {

    static Object lock = new Object();
    static ArrayList<String> datas = new ArrayList<String>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> {
            if (i % 2 == 0) {
                return new ConsumerThread("consumer " + i);
            } else return new ProducerThread("producer " + i);
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

class ConsumerThread extends Thread {

    public ConsumerThread(String string) {
        this.setName(string);
    }

    public void run() {
        while (true) {
            synchronized (ProducerAndConsumerModel03.lock) {
                while (ProducerAndConsumerModel03.datas.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " into wait ,because empty ");
                    try {
                        ProducerAndConsumerModel03.lock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " wait finished ");
                }
                if (ProducerAndConsumerModel03.datas.isEmpty()) {
                    System.out.println("impossible empty !! " + Thread.currentThread().getName());
                    System.exit(-1);
                }
                ProducerAndConsumerModel03.datas.forEach(s -> System.out.println(s));
                ProducerAndConsumerModel03.datas.clear();
                ProducerAndConsumerModel03.lock.notifyAll();
            }
        }
    }
}

class ProducerThread extends Thread {
    public ProducerThread(String string) {
        this.setName(string);
    }

    public void run() {
        while (true) {
            synchronized (ProducerAndConsumerModel03.lock) {
                while (ProducerAndConsumerModel03.datas.size() > 1) {
                    System.out.println(Thread.currentThread().getName() + " into wait,because full ");
                    try {
                        ProducerAndConsumerModel03.lock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + "wait finished ");
                }
                if (ProducerAndConsumerModel03.datas.size() > 1) {
                    System.out.println("impossible full !! " + Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i -> ProducerAndConsumerModel03.datas.add(i + " data"));
                ProducerAndConsumerModel03.lock.notifyAll();
            }
        }
    }
}

