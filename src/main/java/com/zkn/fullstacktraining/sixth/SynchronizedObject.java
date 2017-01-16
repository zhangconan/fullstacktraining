package com.zkn.fullstacktraining.sixth;

/**
 * Created by wb-zhangkenan on 2017/1/16.
 * 1 说说下面的几个方法，分别锁的是什么东西？
 * public static synchronized void doIt(){xx};
 * pubilc  syncronzied void doIt() {xxx)
 * pubilc void doIt(){ syncronized(myobj) ....}
 */
public class SynchronizedObject {
    private static String A = "A";
    /**
     * 对于静态同步方法，锁是当前类的Class对象。
     */
    public static synchronized void doItStatic(){

    }
    /**
     * 对于普通同步方法，锁是当前实例对象。
     */
    public synchronized void doIt() {

    }
    /**
     * 对于同步方法块，锁是Synchonized括号里配置的对象。
     */
    public void doItSynchronized(){
        synchronized(SynchronizedObject.class){

        }
    }

    public static void main(String[] args){
        doItStatic();
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        synchronizedObject.doItSynchronized();
    }
}
