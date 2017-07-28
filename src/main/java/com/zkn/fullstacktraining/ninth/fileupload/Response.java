package com.zkn.fullstacktraining.ninth.fileupload;


import java.io.*;

import com.zkn.fullstacktraining.ninth.Constant;
import com.zkn.fullstacktraining.ninth.fileupload2.Request;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

/**
 * Created by wb-zhangkenan on 2016/12/29.
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

    public void sendStaticResource(String path) throws IOException {

        FileInputStream fis = null;
        try {
            File file = new File(Constant.rootPathHome, path);
            if (file.exists() && !file.isDirectory()) {
                if (file.canRead()) {
                    fis = new FileInputStream(file);
                    int flag = 0;
                    byte[] bytes = new byte[1024];
                    while ((flag = fis.read(bytes)) != -1) {
                        outputStream.write(bytes);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public void processMsp() {

        String[] roots = new String[]{Constant.rootPathHome};
        try {
            //Groovy脚本引擎
            GroovyScriptEngine engine = new GroovyScriptEngine(roots);
            Binding binding = new Binding();
            binding.setVariable("name", request.getParameters().get("name"));
            binding.setVariable("password", request.getParameters().get("password"));
            Object obj = engine.run(request.getUri().substring(1), binding);
            PrintWriter pw = getWriter();
            //这里用PrintWriter字符输出流，设置自动刷新
            pw.write("HTTP/1.1 200 OK \r\n");
            pw.write("Content-Type: text/html\r\n");
            pw.write("Content-Length: " + ((String) obj).length() + "\r\n");
            pw.write("\r\n");
            pw.write((String) obj);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void processFileUpload() {
        String[] roots = new String[]{Constant.rootPathHome};
        try {
            PrintWriter pw = getWriter();
            //这里用PrintWriter字符输出流，设置自动刷新
            pw.write("HTTP/1.1 200 OK \r\n");
            pw.write("Content-Type: text/html;charset=utf-8\r\n");
            pw.write("Content-Length: " + "upload success".length() + "\r\n");
            pw.write("\r\n");
            pw.write("upload success");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
