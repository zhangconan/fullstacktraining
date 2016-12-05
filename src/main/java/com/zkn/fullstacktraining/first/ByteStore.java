package com.zkn.fullstacktraining.first;

import java.util.*;

/**
 * Created by wb-zhangkenan on 2016/12/5.
 * 将MyItem中的元素用字节数组来表示
 */
public class ByteStore {

    private byte[] storeByteArrys = new byte[3000];

    /**
     *将MyItem中的元素用字节数组来表示
     * @param index
     * @param myItem
     */
    public void putMyItem(int index,MyItem myItem){
        if(index < 0 || index >= 1000)
            return;
        int multiply3Index = index*3;
        storeByteArrys[multiply3Index] = myItem.getType();
        storeByteArrys[multiply3Index+1] = myItem.getColor();
        storeByteArrys[multiply3Index+2] = myItem.getPrice();
    }

    public byte[] getStoreByteArrys() {
        return storeByteArrys;
    }

    /**
     *  根据索引值，取出相应的MyItem中的属性值
     * @param index
     * @return
     */
    public MyItem getMyItem(int index){
        int multiply3Index = index*3;
        MyItem myItem = new MyItem(storeByteArrys[multiply3Index],storeByteArrys[multiply3Index+1],storeByteArrys[multiply3Index+2]);
        return myItem;
    }

    public static void main(String[] args){

        ByteStore byteStore = new ByteStore();
        List<MyItem> myItemList = new ArrayList<MyItem>(1000);
        Random random = new Random();
        MyItem myItem = null;
        for(int i=0;i<1000;i++){
            byte[] bytes = new byte[3];
            //为上面的字节数组填充随机值
            random.nextBytes(bytes);
            myItem = new MyItem(bytes[0],bytes[1],bytes[2]);
            System.out.println(myItem.getAllFields());
            myItemList.add(myItem);
            //将MyItem中的属性值用字节数组表示
            byteStore.putMyItem(i,myItem);
        }
        for(int i=0;i<3;i++){
            System.out.println(myItemList.get(i).getAllFields());
            System.out.println(byteStore.getMyItem(i).getAllFields());
            System.out.println(myItemList.get(i).equals(byteStore.getMyItem(i)));
            System.out.println("原先的对象："+myItemList.get(i));
            System.out.println("getMyItem返回的对象："+byteStore.getMyItem(i));
        }
        Collections.sort(myItemList);
        for(int i=0;i<100;i++){
            System.out.println(myItemList.get(i).getAllFields());
        }
    }
}
