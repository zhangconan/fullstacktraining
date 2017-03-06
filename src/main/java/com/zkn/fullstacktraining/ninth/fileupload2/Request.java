package com.zkn.fullstacktraining.ninth.fileupload2;

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
    private Map<String, String> headers = new HashMap<>();
    /**
     * 参数信息
     */
    private Map<String, String> parameters = new HashMap<>();
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

    public void parseRequest() {

        LineNumberReader br = new LineNumberReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String str = null;
        try {
            //读取请求行
            String requestLine = br.readLine();
            if (!StringUtils.isEmpty(requestLine)) {
                sb.append(requestLine);
                String[] reqs = requestLine.split(" ");
                if (reqs != null && reqs.length > 0) {
                    if ("GET".equals(reqs[0])) {
                        method = "GET";
                    } else {
                        method = "POST";
                    }
                }
            }
            //读取请求头
            while ((str = br.readLine()) != null) {
                if ("".equals(str)) {
                    break;
                }
                if (!StringUtils.isEmpty(str)) {
                    if (str.indexOf(":") > 0) {
                        String[] strs = str.split(":");
                        headers.put(strs[0].toLowerCase(), strs[1].trim());
                    }
                }
                sb.append(str).append("\n");
            }
            //POST请求，Content-type为 multipart/form-data
            String contentType = null;
            if ("POST".equals(method) && ((contentType = headers.get("content-type")) != null && headers.get("content-type").startsWith("multipart/form-data"))) {
                //文件上传的分割位 这里只处理单个文件的上传
                String boundary = contentType.substring(contentType.indexOf("boundary") + "boundary=".length());
                //解析消息体
                while ((str = br.readLine()) != null) {
                    //解析结束的标记
                    do {
                        //读取boundary中的内容
                        //读取Content-Disposition
                        str = br.readLine();
                        //说明是文件上传
                        if (str.indexOf("Content-Disposition:") >= 0 && str.indexOf("filename") > 0) {
                            str = str.substring("Content-Disposition:".length());
                            String[] strs = str.split(";");
                            String fileName = strs[strs.length - 1].replace("\"", "").split("=")[1];
                            System.out.println("fileName = " + fileName);
                            //这一行是Content-Type
                            br.readLine();
                            //这一行是换行
                            br.readLine();
                            //正式去读文件的内容
                            BufferedWriter bw = null;
                            try {
                                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("G:\\LearnVideo\\fileLoad" + File.separator + fileName), "UTF-8"));
                                while (true) {
                                    str = br.readLine();
                                    if (str.startsWith("--" + boundary)) {
                                        break;
                                    }
                                    bw.write(str);
                                    bw.newLine();
                                }
                                bw.flush();
                            } catch (Exception e) {

                            } finally {
                                if (bw != null)
                                    bw.close();
                            }
                        }
                        if (str.indexOf("Content-Disposition:") >= 0) {
                            str = str.substring("Content-Disposition:".length());
                            String[] strs = str.split(";");
                            String name = strs[strs.length - 1].replace("\"", "").split("=")[1];
                            br.readLine();
                            StringBuilder stringBuilder = new StringBuilder();
                            while (true) {
                                str = br.readLine();
                                if (str.startsWith("--" + boundary)) {
                                    break;
                                }
                                stringBuilder.append(str);
                            }
                            parameters.put(name, stringBuilder.toString());
                        }
                    } while (("--" + boundary).equals(str));

                    if (str.equals("--" + boundary + "--")) {
                        break;
                    }
                }
            }
            //System.out.println(sb.toString());
            uri = StringUtils.parserUri(sb.toString(), " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUri() {
        return uri;
    }

    //处理文件上传
    public void processFileUpload() {
        //inputStream
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getContentLength() {

        if (headers.get("content-length") != null) {
            return Integer.parseInt(headers.get("content-length"));
        }
        return 0;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
