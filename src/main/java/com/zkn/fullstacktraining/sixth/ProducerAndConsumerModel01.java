package com.zkn.fullstacktraining.sixth;

/**
 * Created by wb-zhangkenan on 2017/1/16.
 * 说说下面的代码为什么是错误的
 */
public class ProducerAndConsumerModel01 {

    private Object obj = new Object();

    public void doIt() {

        synchronized (obj) {
            if (true) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 上面的这段代码应该用while而不是if来进行条件的判断.<<Effective Java>>中文版第187页。总是使用wait循环模式来调用wait方法。
     * 循环被用于在等待的前后测试条件。if只能在等待之前测试条件。
     *  在等待之前测试条件，如果条件已经成立的话则跳过等待，这对于确保活性是必要的。如果条件已经成立，并且在线程等待之前notify(或者notifyAll)
     * 方法已经被调用过，则无法保证该线程将总会从等待中醒过来。（因为if只能执行一次，所以有可能会发生：条件可能已经成立了，线程也已经调用notify
     * 或者notifyAll了，但是由于if只能执行一次，所以等待的线程可能会有执行不到的情况，）
     *  在等待之后测试条件。如果条件不成立的话继续等待，这对确保安全性是必要的。当条件不成立的时候，如果线程继续执行，则可能会破坏被锁保护的约束关系。
     * 当条件不成立时，有下面一些理由可使一个线程醒过来：
     *  1、另一个线程可能得到了锁，并且在一个线程调用notify的时刻，到等待线程醒过来的时刻之间，得到锁的线程已经改变了被保护的状态。
     *  2、条件并没有成立，但是另一个线程可能意外地或恶意的调用了notify。在公有可访问的对象上等待，这些类实际上把自己暴露在危险的境地中。在一个公有
     *      可访问对象的同步方法中包含的wait都会出现这样的问题。
     *  3、在没有被通知的情况下等待线程也可能会醒过来，这被称为伪唤醒。
     *  4、通知线程在唤醒等待线程时可能会过度“大方”。例如，即使只有某一些等待线程的条件已经被满足，但是通知线程仍必须调用notifyAll。
     */
    /**
     * 等待/通知的范式：
     *  等待方遵循如下原则：
     *      1)、获取对象的锁。
     *      2）、如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件(注意，被通知后仍要检查条件)。
     *      3)、条件满足则执行相应的逻辑。
     *  synchronized(对象){
     *      while(条件不满足){
     *          对象.wait();
     *      }
     *      对应的逻辑处理.
     *  }
     *  通知方遵循如下原则：
     *      1）、获得对象的锁。
     *      2)、改变条件。
     *      3）、通知所有等待在对象上的线程。
     *  synchronized(对象) {
     *       改变条件
     *       对象.notifyAll();
     *  }
     */
}















