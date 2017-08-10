package com.zkn.fullstacktraining.spring.one.requestresponse;

import com.zkn.fullstacktraining.spring.one.servlet.Cookie;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zkn on 2017/7/28.
 */
public class Response {
    /**
     * 输出流
     */
    private OutputStream outputStream;
    /**
     * 字符输出流
     */
    private PrintWriter printWriter;
    /**
     * 请求类
     */
    private Request request;
    /**
     * Cookie信息
     */
    private List<Cookie> cookieList = new ArrayList<>(2);

    public Response(OutputStream outputStream, Request request) {
        this.outputStream = outputStream;
        this.request = request;
    }

    public void sendStaticResource(String path) {
        FileInputStream fis = null;
        try {
            if ("/".equals(path)) {
                path = "/static/html/index.html";
            } else {
                path = request.getUri();
            }
            URL url = this.getClass().getResource(path);
            if (url != null) {
                File file = new File(url.getFile());
                if (file.exists() && !file.isDirectory() && file.canRead()) {
                    fis = new FileInputStream(file);
                    int flag = 0;
                    byte[] bytes = new byte[1024];
                    while ((flag = fis.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, flag);
                    }
                }
            } else {
                PrintWriter printWriter = getWriter();
                //这里用PrintWriter字符输出流，设置自动刷新
                printWriter.write("HTTP/1.1 404 File Not Found \r\n");
                printWriter.write("Content-Type: text/html\r\n");
                printWriter.write("Content-Length: 23\r\n");
                printWriter.write("\r\n");
                printWriter.write("<h1>File Not Found</h1>");
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public PrintWriter getWriter() throws IOException {
        printWriter = new PrintWriter(outputStream, true);
        return printWriter;
    }

    public void sendSuccess() {
        PrintWriter printWriter = null;
        try {
            printWriter = getWriter();
            //这里用PrintWriter字符输出流，设置自动刷新
            printWriter.write("HTTP/1.1 200 OK \r\n");
            printWriter.write("Content-Type: text/html;charset=utf-8\r\n");
            printWriter.write("Content-Length: " + "成功了".getBytes().length + "\r\n");
            if (cookieList != null && !cookieList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cookieList.size(); i++) {
                    //设置多个Cookie
                    sb.append("Set-Cookie: ").append(cookieList.get(i).getKey()).append("=").append(cookieList.get(i).getValue()).append("\r\n");
//                    if (i < (cookieList.size() - 1)) {
//                        sb.append("; ");
//                    }
                }
                //sb.append("\r\n");
                printWriter.write(sb.toString());
            }
            printWriter.write("\r\n");
            printWriter.write("成功了");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

    public void addCookie(Cookie cookie) {
        cookieList.add(cookie);
    }
}
