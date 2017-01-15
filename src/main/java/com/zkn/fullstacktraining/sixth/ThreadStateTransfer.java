package com.zkn.fullstacktraining.sixth;

/**
 * Created by zkn on 2017/1/15.
 * 线程状态的流转：
 */
public class ThreadStateTransfer {
    /**
     * 线程状态有这几种（在Thread中的State枚举类里）：
     *  NEW：创建状态。线程刚创建，还没有执行start()方法。
     *  RUNNABLE:可运行状态。线程在虚拟机里是可以执行的状态，但是它可能在等待操作系统的资源，例如处理器等。
     *  BLOCKED：阻塞状态。线程在等待一个监视器锁。处于阻塞状态的线程在等待一个监视器锁，以便进入同步方法或者代码块中。或者是调用了Object.wait()方法，重新进入同步方法或者代码块。
     *  WAITING：等待状态。线程可以通过调用下面的方法进入等待状态：Object.wait()、Thread.join()、LockSupport.park(没有用过)。如果在某一个对象上调用了wait()，则需要在这个对象上调用notify或者notifyAll
     *            以便唤醒等待的线程。如果是线程调用了hread.join()方法，则线程在等待调用join()方法的线程终止。
     *  TIMED_WAITING:定时等待状态(Timed：同步的，定时的，时控的，不是超时的意思)。线程可以通过调用以下方法进入定时等待状态：Thread.sleep、Object.wait()、Thread.join()、LockSupport.parkNanos、LockSupport.parkUntil
     *  TERMINATED:终止状态。线程执行完毕之后会变成终止状态。
     */
    /**
     * 线程状态的流转：
     *  线程在实例化之后会变成NEW的状态，在调用Start()方法之后会变成可执行状态（RUNNABLE），RUNNABLE状态在等待CPU调度，在得到CPU调度之后会变成运行(RUNNING)状态,如果正常运行的话，运行之后会变成终止状态（TERMINATED）。
     *  线程在RUNNING状态的时候可能发生的线程状态流转：
     *      1、如果遇到线程调用sleep方法、join()方法或者I/O interrupt，则会变成“BLOCKED”，在sleep()timeout、joins()timeout或者join执行完之后、或者调用interrupt()方法，则会变成RUNNABLE状态。
     *      2、如果遇到线程调用wait()方法、或者释放锁之后，则会进入对象等待池中。直到遇到相同对象调用notify()或者notifyAll()方法或者遇到interrupt()方法，则会进入等锁池中，等待获得监视器锁，变成RUNNABLE状态。
     *      3、如果遇到同步代码块(synchronized)，则会进入到等锁池中，等待获得监视器锁，变成RUNNABLE状态。
     */
}














