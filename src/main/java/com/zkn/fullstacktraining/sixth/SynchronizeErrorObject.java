package com.zkn.fullstacktraining.sixth;

/**
 * Created by wb-zhangkenan on 2017/1/16.
 * 说说为什么下面的代码是错误的
 */
public class SynchronizeErrorObject {

    public void doIt() {

        synchronized (new SynchronizeErrorObject()){

        }
    }
    /**
     * 因为对于同步代码块，锁是synchronized的括号里配置的对象，而上面的这段代码中配置的对象每次都是新实例化的对象，
     * 即每次锁的都是不同的东西，起不到同步的效果。
     */
}
