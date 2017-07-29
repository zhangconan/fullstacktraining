package com.zkn.fullstacktraining.spring.one.requestresponse;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
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
    public void parseRequest(InputStream inputStream) {

        if (isParser) {
            return;
        }
        this.inputStream = inputStream;
        //这里用了LineNumberReader 没有考虑性能问题
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));
        try {
            //获取请求行 请求行格式 Method URI 协议
            String str = lineNumberReader.readLine();
            String[] strArray = str.split(" ");
            requestMethod = strArray[0];
            parseUrl(strArray[1]);
            parseHeader(lineNumberReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析头信息
     *
     * @param lineNumberReader
     */
    private void parseHeader(LineNumberReader lineNumberReader) throws IOException {
        String headerStr = null;
        String[] str = null;
        while ((headerStr = lineNumberReader.readLine()) != null) {
            str = headerStr.split(":");
            if (str.length == 2) {
                headerMap.put(str[0].toLowerCase(), str[1]);
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
                Object value = null;
                for (String str : strArray) {
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
            }
            return;
        }
        uri = s;
    }

    public String getUri() {
        return uri;
    }
}
