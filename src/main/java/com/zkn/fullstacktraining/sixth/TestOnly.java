package com.zkn.fullstacktraining.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * Created by zkn on 2017/1/15.
 */
public class TestOnly {
    static Object lock=new Object();
    static ArrayList<String> datas=new  ArrayList<String>();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10).mapToObj(i->{if(i%2==0) {return new MThread("consumer "+i);} else return new NThread("producer "+i);}).filter(t->{t.start();return true;}).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}

class MThread extends Thread
{

    public MThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                if(TestOnly.datas.isEmpty())
                {
                    System.out.println(Thread.currentThread().getName()+" into wait ,because empty ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+" wait finished ");
                }
                if(TestOnly.datas.isEmpty())
                {

                    System.out.println("impossible empty !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                TestOnly.datas.forEach(s->System.out.println(s));
                TestOnly.datas.clear();

            }
        }
    }
}

class NThread extends Thread
{
    public NThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            synchronized(TestOnly.lock)
            {
                if(TestOnly.datas.size()>1)
                {
                    System.out.println(Thread.currentThread().getName()+" into wait,because full ");
                    try {
                        TestOnly.lock.wait();
                    } catch (InterruptedException e) {
                        break;

                    }
                    System.out.println(Thread.currentThread().getName()+"wait finished ");
                }
                if(TestOnly.datas.size()>1)
                {

                    System.out.println("impossible full !! "+Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i->TestOnly.datas.add(i+" data"));

            }
        }
    }
}
