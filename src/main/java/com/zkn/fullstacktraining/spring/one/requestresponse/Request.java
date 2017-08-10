package com.zkn.fullstacktraining.spring.one.requestresponse;


import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.*;

/**
 * Created by zkn on 2017/7/28.
 */
public class Request {
    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 头文件信息
     */
    private Map<String, String> headerMap = new HashMap<>();
    /**
     * 参数信息
     */
    private Map<String, Object> parameterMap = new HashMap<>();
    /**
     * 是否被解析过
     */
    private boolean isParser = false;
    /**
     * 请求类型
     */
    private String requestMethod;
    /**
     * 请求URL
     */
    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 获取头文件信息
     *
     * @param key
     * @return
     */
    public String getHeader(String key) {
        if (key == null) {
            return null;
        }
        return headerMap.get(key.toLowerCase());
    }

    /**
     * 获取参数的值
     *
     * @param key
     * @return
     */
    public String getParameterValue(String key) {
        Object obj = parameterMap.get(key);
        if (obj == null)
            return null;
        if (obj instanceof List) {
            if (!((List) obj).isEmpty()) {
                return (String) ((List) obj).get(0);
            }
            return null;
        }
        return (String) obj;
    }

    /**
     * 获取多个值
     *
     * @param key
     * @return
     */
    public List<String> getParameterValues(String key) {
        Object obj = parameterMap.get(key);
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            return (List<String>) obj;
        }
        return null;
    }

    /**
     * 获取所有的key
     *
     * @return
     */
    public Set<String> getParameterNames() {

        return parameterMap.keySet();
    }

    public String getRequestMethod() {

        return requestMethod;
    }

    /**
     * 解析请求
     */
    public void parseRequest() {

        if (isParser) {
            return;
        }
        isParser = true;
        //这里用了LineNumberReader 没有考虑性能问题
        BufferedReader lineNumberReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            //获取请求行 请求行格式 Method URI 协议
            String str = lineNumberReader.readLine();
            if (str != null) {
                String[] strArray = str.split(" ");
                requestMethod = strArray[0];
                parseUrl(strArray[1]);
            }
            String headerStr = null;
            String[] strArr = null;
            //解析头信息
            while ((headerStr = lineNumberReader.readLine()) != null) {
                if ("".equals(headerStr)) {
                    break;
                }
                strArr = headerStr.split(":");
                if (strArr.length == 2) {
                    headerMap.put(strArr[0].toLowerCase(), strArr[1].trim());
                }
            }
            //如果是POST请求
            String contentType = null;
            if ("POST".equals(requestMethod)) {
                //文件上传
                if ((contentType = headerMap.get("content-type")) != null && headerMap.get("content-type").startsWith("multipart/form-data")) {
                    //解析文件上传
                    parseUploadFile(lineNumberReader, contentType);
                } else {
                    //非文件上传
                    String postParameter = "";
                    while ((postParameter = lineNumberReader.readLine()) != null) {
                        if ("".equals(postParameter)) {
                            break;
                        }
                        wrapperParameterValue(postParameter);
                    }
                }
            }
            System.out.println(JSON.toJSONString(parameterMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("执行完了。。。");
    }

    private void parseUploadFile(BufferedReader lineNumberReader, String contentType) throws IOException {
        String str;//文件上传的分割位 这里只处理单个文件的上传
        String boundary = contentType.substring(contentType.indexOf("boundary") + "boundary=".length());
        //解析消息体
        while ((str = lineNumberReader.readLine()) != null) {
            //解析结束的标记
            do {
                //读取boundary中的内容
                //读取Content-Disposition
                str = lineNumberReader.readLine();
                //说明是文件上传
                if (str.indexOf("Content-Disposition:") >= 0 && str.indexOf("filename") > 0) {
                    str = str.substring("Content-Disposition:".length());
                    String[] strs = str.split(";");
                    String fileName = strs[strs.length - 1].replace("\"", "").split("=")[1];
                    System.out.println("fileName = " + fileName);
                    //这一行是Content-Type
                    lineNumberReader.readLine();
                    //这一行是换行
                    lineNumberReader.readLine();
                    //正式去读文件的内容
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("G:\\LearnVideo\\fileLoad" + File.separator + fileName), "UTF-8"));
                        while (true) {
                            str = lineNumberReader.readLine();
                            if (str.startsWith("--" + boundary)) {
                                break;
                            }
                            bw.write(str);
                            bw.newLine();
                        }
                        bw.flush();
                    } catch (Exception e) {

                    } finally {
                        if (bw != null) {
                            bw.close();
                        }
                    }
                }
                if (str.indexOf("Content-Disposition:") >= 0) {
                    str = str.substring("Content-Disposition:".length());
                    String[] strs = str.split(";");
                    String name = strs[strs.length - 1].replace("\"", "").split("=")[1];
                    lineNumberReader.readLine();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (true) {
                        str = lineNumberReader.readLine();
                        if (str.startsWith("--" + boundary)) {
                            break;
                        }
                        stringBuilder.append(str);
                    }
                    parameterMap.put(name, stringBuilder.toString());
                }
            } while (("--" + boundary).equals(str));
            //解析结束
            if (str.equals("--" + boundary + "--")) {
                break;
            }
        }
    }

    /**
     * 解析URI
     *
     * @param s
     */
    private void parseUrl(String s) {
        if ("/".equals(s)) {
            uri = "/";
            return;
        }
        String tempStr = s;
        /**
         * 说明可能带参数
         */
        int flag = 0;
        if ((flag = tempStr.indexOf("?")) > 0) {
            uri = tempStr.substring(0, flag);
            if (tempStr.length() > (flag + 1)) {
                tempStr = tempStr.substring(flag + 1, tempStr.length());
                String[] strArray = tempStr.split("&");
                for (String str : strArray) {
                    wrapperParameterValue(str);
                }
            }
            return;
        }
        uri = s;
    }

    /**
     * 组装参数值
     *
     * @param str
     */
    private void wrapperParameterValue(String str) {
        Object value;
        String[] strArr = str.split("=");
        if (strArr.length == 2) {
            value = parameterMap.get(strArr[0]);
            if (value == null) {
                parameterMap.put(strArr[0], strArr[1]);
            } else {
                if (value instanceof List) {
                    ((List) value).add(strArr[1]);
                } else {
                    List<String> list = new ArrayList<>(2);
                    list.add((String) value);
                    list.add(strArr[1]);
                    parameterMap.put(strArr[0], strArr[1]);
                }
            }
        }
    }

    public String getUri() {
        return uri;
    }
}
