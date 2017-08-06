package com.zkn.fullstacktraining.spring.one.socket;

import com.zkn.fullstacktraining.spring.one.context.ApplicationContext;
import com.zkn.fullstacktraining.spring.one.process.StaticResourceProcessor;
import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

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

            }else{
                ApplicationContext.getServlet().service(request,response);
            }
//            if(!StringUtils.isEmpty(uri) && uri.startsWith("/file/uploadFileAction")){
//
//                response.processFileUpload();
//            }
//            if(!StringUtils.isEmpty(uri) && uri.startsWith("/msp/")){
//                MspProcessor mspProcessor = new MspProcessor();
//                mspProcessor.processMsp(response);
//            }
            if("SHUT_DOWN".equals(request.getUri())){
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
