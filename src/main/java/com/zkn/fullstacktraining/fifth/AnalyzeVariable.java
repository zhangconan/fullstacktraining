package com.zkn.fullstacktraining.fifth;

import org.junit.Test;

import java.util.stream.LongStream;

/**
 * Created by wb-zhangkenan on 2017/1/10.*
 * 参考：http://ifeve.com/atomiclong-and-longadder/
 *      http://www.importnew.com/11345.html
 *      http://ifeve.com/java8-striped64-and-longadder/
 */
public class AnalyzeVariable {

    /**
     *  normal:多线程情况下有值被覆盖的风险。
     *  volatile：volatile只是保证了线程每次读取的都是最新值，保证了值的内存可见性。
     *            它不保证数据的同步性。volatile使用于一个线程写，多个线程读的场景。
     *  synchronized：当一个线程读写的时候，其他的线程都被阻塞。它可以保证数据的同步性。
     *  AtomicLong：可以保证数据的同步性。通过使用CAS（比较并交换）处理器指令来更新计数器的值。
     *              它通过一个直接机器码指令设置值时，能够最小程度地影响其他线程的执行。
     *              但是如果它在与其他线程竞争设置值时失败了，它不得不再次尝试。在高竞争下，
     *              这将转化为一个自旋锁，线程不得不持续尝试设置值，无限循环直到成功。
     *  LongAdder:减少并发，将单一value的更新压力分担到多个value中去，降低单个value的 “热度”，
     *             分段更新。这样，线程数再多也会分担到多个value上去更新，只需要增加value就可以降低
     *             value的 “热度”。牺牲空间来换取时间。
     */


    @Test
    public void testStream(){
        LongStream.of(0).map(e->{
            for(int i=0;i<10;i++){
            Thread thread = new Thread(()->{
                for(int j=0;j<100000;j++)
                    //e++;
                    System.out.println(111);
            });
            thread.start();
        }
        return e;}).limit(10).max();
    }
}
