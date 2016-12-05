package com.zkn.fullstacktraining.first;

/**
 * Created by wb-zhangkenan on 2016/12/5.
 */
public class MyItem {
    /**
     * 类型
     */
    private byte type;
    /**
     * 颜色
     */
    private byte color;
    /**
     * 价格
     */
    private byte price;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getColor() {
        return color;
    }

    public void setColor(byte color) {
        this.color = color;
    }

    public byte getPrice() {
        return price;
    }

    public void setPrice(byte price) {
        this.price = price;
    }

    public MyItem() {
    }

    public MyItem(byte type, byte color, byte price) {
        this.type = type;
        this.color = color;
        this.price = price;
    }

    public String getAllFields() {

        return "MyItem{" +
                "type=" + type +
                ", color=" + color +
                ", price=" + price +
                '}';
    }
}
