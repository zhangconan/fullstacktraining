package com.zkn.fullstacktraining.ninth.fileupload;

import com.zkn.fullstacktraining.util.StringUtils;
import java.io.IOException;
import java.net.Socket;
import com.zkn.fullstacktraining.ninth.fileupload2.Request;
/**
 * Created by wb-zhangkenan on 2017/3/6.
 *
 * @author wb-zhangkenan
 * @date 2017/03/06
 */
public class ProcessSocket implements Runnable {

    private Socket socket;

    public ProcessSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Request request = null;
        try {
            request = new Request(socket.getInputStream());
            request.parseRequest();//解析请求信息
            Response response = new Response(socket.getOutputStream(),request);
            String uri = request.getUri();
            if(uri !=null && uri.startsWith("/favicon.ico")){

            }
            if(!StringUtils.isEmpty(uri) && uri.startsWith("/static/")){
                StaticResourceProcessor resouce = new StaticResourceProcessor();
                //处理静态资源
                resouce.process(request,response);
            }
            if(!StringUtils.isEmpty(uri) && uri.startsWith("/file/uploadFileAction")){
                //处理上传文件信息
                //request
                System.out.println("dsdsdsd");
            }
            if(!StringUtils.isEmpty(uri) && uri.startsWith("/msp/")){
                MspProcessor mspProcessor = new MspProcessor();
                mspProcessor.processMsp(response);
            }
            socket.close();
            if("SHUT_DOWN".equals(request.getUri())){
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
