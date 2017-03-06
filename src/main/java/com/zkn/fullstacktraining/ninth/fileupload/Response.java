package com.zkn.fullstacktraining.ninth.fileupload;

import java.io.*;

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

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void sendStaticResource(String path) throws IOException {

        FileInputStream fis = null;
        try {
            File file = new File("D:\\CUST\\WORK\\Exercises\\FullStackTraining\\src\\main\\java\\com\\zkn\\fullstacktraining\\ninth\\fileupload", path);
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
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public PrintWriter getWriter() throws IOException {
        printWriter = new PrintWriter(outputStream, true);
        return printWriter;
    }

}
