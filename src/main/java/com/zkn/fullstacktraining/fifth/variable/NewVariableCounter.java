package com.zkn.fullstacktraining.fifth.variable;

import java.util.stream.IntStream;

/**
 * Created by zkn on 2017/1/10.
 */
public class NewVariableCounter {

    private static int counter = 1_000_000;

    public static void main(String[] args){
        getVariable(new AtomicLongVariable());
        getVariable(new LongAdderVariable());
        getVariable(new NormalVariable());
        getVariable(new SynchronizeVariable());
        getVariable(new VolatileVariable());
    }

    public static void getVariable(MyCounter myCounter){
        //MyCounter variable = myCounter;
        long startTime = System.currentTimeMillis();
        IntStream.range(0,10).forEach(e->new Thread(()->{for(int i = 0; i<counter; i++)myCounter.incr();}).start());
        while (true){
            if(Thread.activeCount() == 2){//debug下这里的值为1
                System.out.println(myCounter.getCurValue());
                System.out.println(VariableEnum.getDesc(myCounter.getClass())+(System.currentTimeMillis()-startTime));
                break;
            }
        }
    }
}
