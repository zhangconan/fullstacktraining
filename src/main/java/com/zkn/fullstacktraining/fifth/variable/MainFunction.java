package com.zkn.fullstacktraining.fifth.variable;

import org.junit.Test;

/**
 * Created by zkn on 2017/1/9.
 */
public class MainFunction {

    public static void main(String[] args) {

    }
    @Test
    public void testNormalVariable(){
        NormalVariable normalVariable = new NormalVariable();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000000;i++){
                        normalVariable.incr();
                    }
                }
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                System.out.println(normalVariable.getCurValue());
                Thread.currentThread().getThreadGroup().list();
                break;
            }
        }
    }
    @Test
    public void testVolaitleVariable(){
        VolaitleVariable volaitleVariable = new VolaitleVariable();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000000;i++){
                        volaitleVariable.incr();
                    }
                }
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                System.out.println(volaitleVariable.getCurValue());
                Thread.currentThread().getThreadGroup().list();
                break;
            }
        }
    }
    @Test
    public void testSynchronizeVariable(){
        SynchronizeVariable synchronizeVariable = new SynchronizeVariable();
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000000; j++) {
                        synchronizeVariable.incr();
                    }
                }
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                System.out.println(synchronizeVariable.getCurValue());
                Thread.currentThread().getThreadGroup().list();
                break;
            }
        }
    }
    @Test
    public void testAtomicLongVariable(){
        AtomicLongVariable atomicLongVariable = new AtomicLongVariable();
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000000; j++) {
                        atomicLongVariable.incr();
                    }
                }
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                System.out.println(atomicLongVariable.getCurValue());
                Thread.currentThread().getThreadGroup().list();
                break;
            }
        }
    }
    @Test
    public void testLongAdderVariable(){
        LongAdderVariable longAdderVariable = new LongAdderVariable();
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000000; j++) {
                        longAdderVariable.incr();
                    }
                }
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                System.out.println(longAdderVariable.getCurValue());
                Thread.currentThread().getThreadGroup().list();
                break;
            }
        }
    }
}
