package com.zkn.fullstacktraining.spring.one.socket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zkn.fullstacktraining.spring.one.context.ApplicationContext;
import com.zkn.fullstacktraining.spring.one.servlet.impl.HttpServletImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class HttpServer {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        await();
    }

    private static void await() {

        ServerSocket serverSocket = null;
        try {
            ApplicationContext ac = new ApplicationContext(new HttpServletImpl(), "/com/zkn/fullstacktraining/spring/one/spring-config.properties");
            ac.init();
            boolean shutDown = false;
            //创建一个服务端
            serverSocket = new ServerSocket(8005, 1, InetAddress.getByName("127.0.0.1"));
            ExecutorService executorService = new ThreadPoolExecutor(10,
                    10, 0L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(),
                    new ThreadFactoryBuilder().setNameFormat("XX-task-%d").build());
            while (!shutDown) {
                //接收客户端请求
                ProcessSocket processSocket = new ProcessSocket(serverSocket.accept());
                executorService.execute(processSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
