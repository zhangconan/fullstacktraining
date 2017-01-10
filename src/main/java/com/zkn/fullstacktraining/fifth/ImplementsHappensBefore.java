package com.zkn.fullstacktraining.fifth;

/**
 * Created by wb-zhangkenan on 2017/1/10.
 * 有几种方式能实现多个线程共享变量之间的happens-before方式
 */
public class ImplementsHappensBefore {
    /**
     * 1、用syncronized 关键字。
     * 2、用lock方法。
     * 3、用volatile关键字。
     * 4、用原子类型。在java.util.concurrent.atomic包下。如：AtomicInteger、AtomicDouble。
     * 5、对于64位的类型，可以用LongAdder、DoubleAdder.
     * 6、用Unsafe类。
     */
}
