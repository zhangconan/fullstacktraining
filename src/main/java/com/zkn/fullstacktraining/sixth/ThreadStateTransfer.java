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
    /**
     * 线程调用相关的方法：
     *  wait()：让一个线程处于等待(阻塞)状态，并且释放所持有的对象的锁，进入对象的等待池中。只有调用对象的notify()方法（或notifyAll()方法）时才能唤醒
     *      等待池中的线程进入等锁池（lock pool），如果线程重新获得对象的锁就可以进入就绪状态。
     *  sleep():使一个正在运行的线程处于睡眠状态，不会释放对象的锁。因此休眠时间结束后会自动回到就绪状态，调用此方法要处理InterruptedException异常。
     *  notify():唤醒一个处于等待状态的线程，在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且与优先级无关。
     *  notifyAll():唤醒所有处于等待状态的线程，该方法不是将对象的锁给所有线程，而是让他们竞争，只有获得锁（监视器锁）的线程才能进入就绪状态（RUNNABLE）。
     */
    /**
     *线程的sleep()方法和yield()方法有什么区别？
     *① sleep()方法给其他线程运行机会时不考虑线程的优先级，因此会给低优先级的线程以运行的机会；yield()方法只会给相同优先级或更高优先级的线程以运行的机会；
     *② 线程执行sleep()方法后转入阻塞（blocked）状态，而执行yield()方法后转入就绪（RUNNABLE）状态；
     *③ sleep()方法声明抛出InterruptedException，而yield()方法没有声明任何异常；
     *④ sleep()方法比yield()方法（跟操作系统CPU调度相关）具有更好的可移植性。
     */
}














