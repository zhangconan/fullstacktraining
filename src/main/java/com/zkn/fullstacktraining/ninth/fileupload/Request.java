package com.zkn.fullstacktraining.ninth.fileupload;

import com.zkn.fullstacktraining.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class Request {
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * uri
     */
    private String uri;
    /**
     * 请求头的信息
     */
    private Map<String,String> headers = new HashMap<>();
    /**
     * 请求类型
     */
    private String method;
    /**
     * 内容的长度
     */
    private int contentLength;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parseRequest(){

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String str = null;
        try {
            //读取请求行
            String requestLine = br.readLine();
            if(!StringUtils.isEmpty(requestLine)){
                sb.append(requestLine);
                String[] reqs = requestLine.split(" ");
                if(reqs!= null && reqs.length>0){
                    if("GET".equals(reqs[0])){
                        method = "GET";
                    }else{
                        method = "POST";
                    }
                }
            }
            //请求头
            while((str = br.readLine()) != null){
                if("".equals(str)) {
                    break;
                }
                if(!StringUtils.isEmpty(str)){
                    if(str.indexOf(":") > 0){
                        String[] strs = str.split(":");
                        headers.put(strs[0].toLowerCase(),strs[1].trim());
                    }
                }
                sb.append(str).append("\n");
            }
            System.out.println(sb.toString());
            uri = StringUtils.parserUri(sb.toString()," ");
//            //POST的内容
//            byte[] bytes = new byte[getContentLength()];
//            inputStream.read(bytes);
//            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUri() {
        return uri;
    }

    //处理文件上传
    public void processFileUpload(){
        //inputStream
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getContentLength() {

        if(headers.get("content-length") != null){
            return Integer.parseInt(headers.get("content-length"));
        }
        return 0;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
