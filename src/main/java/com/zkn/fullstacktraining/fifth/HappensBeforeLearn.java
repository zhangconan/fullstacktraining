package com.zkn.fullstacktraining.fifth;

/**
 * Created by wb-zhangkenan on 2017/1/9.
 * 解释下你所理解的Happens-before含义和JVM里的几个Happens-before约定
 * 参考：http://www.iteye.com/topic/260515/
 *      深入理解Java虚拟机：JVM高级特性与最佳实践（第12章）
 *      Java并发编程的艺术（第三章）
 */
public class HappensBeforeLearn {

    /**
     *  Happens-before：Happens-before是保证了数据的可见性。如果操作A happens-before B,那么操作A所产生影响能被
     *      操作B观察到，即操作A产生的影响是对操作B可见的。这里所说的影响主要是对共享变量值的修改。并且操作A的执行顺序在操作B
     *      之前。
     *          如果两个操作之间存在Happens-before关系，并不意味着Java平台的实现一定要按照Happens-before关系指定的顺序
     *      来执行。如果重排序之后的执行结果，与按happens-before关系来执行的结果一致，那么这种重排序并不非法（也就是说，JMM
     *      允许这种重排序）。
     *  Happens-before的约定：
     *      单线程规则：
     *          在同一个线程中，写在前面的操作happen-before写在后面的操作。
     *      监视线程锁定规则：
     *          对锁的unlock操作happens-before后续的对同一个锁的lock操作。这里的后续指的是时间上的先后关系。
     *          这里必须强调的是同一个锁。
     *      volatile变量规则：
     *          对一个volatile变量的写操作happens-before于后续对这个变量的读操作。这里的后续指的是时间上的先后关系。
     *      线程启动规则：
     *          如果线程A执行操作ThreadB.start(),那么线程A的ThreadB.start()操作happens-before于线程B中的任意操作。
     *      线程终止规则（join）：
     *          如果在线程A中执行ThreadB.join()操作，并成功返回（等ThreadB执行结束之后），那么在线程A中可以读取到ThreadB
     *          对共享变量修改之后的值。
     *      传递性规则：
     *          如果有操作A happens-before 操作B，操作B happens-before 操作C，则由操作A happens-before 操作C。
     */
}
