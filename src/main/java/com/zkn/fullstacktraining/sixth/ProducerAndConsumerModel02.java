package com.zkn.fullstacktraining.sixth;

import java.util.stream.IntStream;

/**
 * Created by wb-zhangkenan on 2017/1/16.
 */
public class ProducerAndConsumerModel02 {

    public static void main(String[] args){
        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();
        /**
         * 10个生产者。
         */
        IntStream.range(0,10).forEach((e)->{Thread thread = new Thread(()->producerAndConsumer.producer());thread.start();});
        /**
         * 10个消费者
         */
        IntStream.range(0,10).forEach((e)->{Thread thread = new Thread(()->producerAndConsumer.consumer());thread.start();});
    }
}
class ProducerAndConsumer{

    private static Object obj = new Object();
    private boolean flag = false;

    public void producer(){

        synchronized (obj){
            if (flag){
                try {
                    System.out.println("1111生成者"+Thread.currentThread().getName());
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("生产者flag="+flag+"  "+Thread.currentThread().getName());
            flag = true;
            obj.notifyAll();
        }
    }

    public void consumer(){

        synchronized (obj){
            if (!flag) {
                try {
                    System.out.println("2222消费者"+Thread.currentThread().getName());
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("消费者flag="+flag+"  "+Thread.currentThread().getName());
            flag = false;
            obj.notifyAll();
        }
    }
}