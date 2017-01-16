package com.zkn.fullstacktraining.sixth;

/**
 * Created by wb-zhangkenan on 2017/1/16.
 * @see com/zkn/fullstacktraining/sixth/monitor.PNG
 */
public class SynchronizedAnalyse {
    /**
     * JDK1.8 javap -v 类名
     * 对于同步块的实现使用了monitorenter和monitorexit指令，而同步方法则是依靠方法修饰符上的ACC_SYNCHRONIZED来完成的。
     * 无论采用哪种方式，其本质是对一个对象的监视器（monitor）进行获取，而这个获取过程是排他的，也就是同一时刻只能有一个
     * 线程获取到由synchronized所保护对象的监视器。
     * 任意一个对象都拥有自己的监视器，当这个对象由同步块或者这个对象的同步方法调用时，执行方法的线程必须先获取到该对象的监视器
     * 才能进入同步块或者同步方法，而没有获取到监视器（执行该方法）的线程将会被阻塞在同步块和同步方法的入口处，进入BLOCKED状态。
     */
    public static void main(String[] args){

        synchronized (SynchronizedAnalyse.class){

        }
        testStatic();
    }

    public synchronized void test(){

    }

    public synchronized static void testStatic(){

    }
}
