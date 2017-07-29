package com.zkn.fullstacktraining.spring.one.requestresponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

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

    public Response(OutputStream outputStream, Request request) {
        this.outputStream = outputStream;
        this.request = request;
    }

    public void sendStaticResource(String path) {
        InputStream is = null;
        try {
            if ("/".equals(path)) {
                path = "/static/html/index.html";
            }
            is = this.getClass().getResourceAsStream(path);
            int flag = 0;
            byte[] bytes = new byte[1024];
            if (is != null) {
                while ((flag = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, flag);
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
            if (outputStream != null) {
                try {
                    outputStream.close();
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
            printWriter.write("Content-Type: text/html\r\n");
            printWriter.write("Content-Length: 23\r\n");
            printWriter.write("\r\n");
            printWriter.write("<h1>成功了</h1>");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }

    }
}
