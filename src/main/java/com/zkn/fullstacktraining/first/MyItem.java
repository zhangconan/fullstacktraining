package com.zkn.fullstacktraining.first;

/**
 * Created by wb-zhangkenan on 2016/12/5.
 */
public class MyItem implements Comparable{
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

    /**
     * 重写equals方法。这里需要注意：
     *  传入的参数必须是Object。
     *  判断 == 是不是相等。
     *  判断是不是同一个class对象。
     *  判断属性值是不是相等。
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MyItem myItem = (MyItem) o;

        if (type != myItem.type) return false;
        if (color != myItem.color) return false;
        return price == myItem.price;

    }

    /**
     * result为任意定义的一个素数，这里倍数一般都为31.任何数n * 31就可以被JVM优化为 (n << 5)-n
     * @return
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + type;
        result = 31 * result + color;
        result = 31 * result + price;
        return result;
    }

    @Override
    public int compareTo(Object o) {

        return this.getPrice() - ((MyItem)o).getPrice();
    }
}
