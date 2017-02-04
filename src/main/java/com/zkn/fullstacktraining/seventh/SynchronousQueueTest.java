package com.zkn.fullstacktraining.seventh;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by wb-zhangkenan on 2017/2/4.
 * 参考
 *  @link http://www.cnblogs.com/wanly3643/p/3904681.html
 *  @link http://ifeve.com/java-synchronousqueue/
 *  说说下面的代码为什么会输出faield
 *  if (queue.offer("S1")) {
 *      System.out.println("scucess");
 *   } else {
 *       System.out.println("faield");
 *   }
 *  SynchronousQueue的内部不能缓存元素，不能调用peek()方法来查看队列中有没有数据元素，也不能对它遍历操作。
 *      队列头元素是第一个排队要插入数据的线程，而不是要交换的数据。数据是在配对的生产者和消费者线程之间直接传递的。
 *      内部数据的传递是通过transfer来实现的，它有两个实现类，一个是TransferQueue(公平模式),另一个是TransferStack(非公平模式)。
 *      上面的这个例子中只有一个线程去放，没有响应的准备读的线程，所以会输出faield。
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {

        SynchronousQueue<String> queue = new SynchronousQueue();

        Thread thread02 = new Thread(() -> {
            System.out.println("我是来消费数据的!!!");
            String str = null;
            try {
                str = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(str);
        });
        thread02.start();//先启动消费的

        Thread thread01 = new Thread(() -> {
            System.out.println("我来生产数据!!!");
            if (queue.offer("S1")) {
                System.out.println("scucess");
            } else {
                System.out.println("faield");
            }
        });
        thread01.start();
    }
}
