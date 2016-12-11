package com.zkn.fullstacktraining.second;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zkn on 2016/12/11.
 */
public class MergeMp3 {

    public static void main(String[] args){
        //按照文件名排序
        File file = new File("C:\\Users\\zkn\\Downloads\\MP3");
        List<String> list = Lists.newArrayList();
        recursion(file.listFiles(),list);
        System.out.println(Arrays.toString(list.toArray()));
        mergeMaps(list);
    }
    /**
     * 递归
     */
    public static void recursion(File[] files,List<String> list){
        if(files != null && files.length > 0 ){
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    recursion(files[i].listFiles(),list);
                }else{
                    if(files[i].getName().endsWith(".mp3"))
                        list.add(files[i].getAbsolutePath());
                }
            }
        }
    }
    /**
     * 合并mp3
     * @param list
     */
    public static void mergeMaps(List<String> list){
        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\zkn\\Downloads\\MP3\\mergemp3.mp3",true);
            FileInputStream fis = null;
            byte[] bytes = new byte[1024];
            for(int i=0;i<list.size();i++){
                fis = new FileInputStream(new File(list.get(i)));
                while(fis.read(bytes) > 0){
                    fos.write(bytes,0,bytes.length);
                }
                fis.close();
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
