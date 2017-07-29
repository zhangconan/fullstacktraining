package com.zkn.fullstacktraining.spring.one.context;


import java.io.*;

/**
 * Created by wb-zhangkenan on 2017/1/3.
 */
public class FileSystemClassLoader extends ClassLoader{
    /**
     * 应用根路径
     */
    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    public FileSystemClassLoader() {
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getByteData(name);
        return defineClass(null,bytes,0,bytes.length);
    }

    private byte[] getByteData(String path){

        String className = getClassName(path);
        byte[] bytes = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(className);
            //ByteArrayOutputStream内部有一个数组用来保存读取的字节流
            ByteArrayOutputStream baiStream = new ByteArrayOutputStream();
            int flag = 0;
            while ((flag = fis.read(bytes)) != -1){
                /**
                 * 这里需要注意：写入的时候，写入的范围一定是0，flag。
                 * 原因是：有可能读取的bytes不够1024个字节，这个时候如果不写入读取范围的话，
                 *       则会把bytes中存留的上次读取的数据也写入到ByteArrayOutputStream中。
                 *       这样在defineClass的时候会出现异常。
                 *       异常信息如下：
                 *Exception in thread "main" java.lang.ClassFormatError:
                 *      Extra bytes at the end of class file FirstServlet
                 */
                baiStream.write(bytes,0,flag);
            }
            return baiStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private String getClassName(String className){

        if(className == null || "".equals(className)){
             throw new RuntimeException("Class名称不能为空");
        }
        return className;
    }

}
