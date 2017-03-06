package com.zkn.fullstacktraining.ninth.fileupload;

import com.zkn.fullstacktraining.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class HttpServer {

    public static void main(String[] args){

        await();
    }

    private static void await() {

        ServerSocket serverSocket = null;
        try {
            boolean shutDown = false;
            //创建一个服务端
            serverSocket = new ServerSocket(8004,1, InetAddress.getByName("127.0.0.1"));
            while (!shutDown){
                //接收客户端请求
                Socket socket = serverSocket.accept();
                Request request = new Request(socket.getInputStream());
                request.parseRequest();//解析请求信息
                Response response = new Response(socket.getOutputStream());
                String uri = request.getUri();
                if(uri !=null && uri.startsWith("/favicon.ico")){

                }
                if(!StringUtils.isEmpty(uri) && uri.startsWith("/static/")){
                    StaticResourceProcessor resouce = new StaticResourceProcessor();
                    resouce.process(request,response);//处理静态资源
                }
                if(!StringUtils.isEmpty(uri) && uri.startsWith("/file/uploadFileAction")){
                    //处理上传文件信息
                    //request
                    System.out.println("dsdsdsd");
                }
                socket.close();
                shutDown = "SHUT_DOWN".equals(request.getUri());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
