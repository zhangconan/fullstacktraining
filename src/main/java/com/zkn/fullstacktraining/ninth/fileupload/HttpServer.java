package com.zkn.fullstacktraining.ninth.fileupload;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zkn.fullstacktraining.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class HttpServer {

    public static void main(String[] args) {

        await();
    }

    private static void await() {

        ServerSocket serverSocket = null;
        try {
            boolean shutDown = false;
            //创建一个服务端
            serverSocket = new ServerSocket(8004, 1, InetAddress.getByName("127.0.0.1"));
            ExecutorService executorService = new ThreadPoolExecutor(10,
                    10, 0L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(),
                    new ThreadFactoryBuilder().setNameFormat("XX-task-%d").build());
            while (!shutDown) {
                //接收客户端请求
                ProcessSocket processSocket = new ProcessSocket(serverSocket.accept());
                executorService.execute(processSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
