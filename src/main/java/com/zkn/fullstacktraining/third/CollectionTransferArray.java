package com.zkn.fullstacktraining.third;

import com.zkn.fullstacktraining.first.RandomString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zkn on 2016/12/21.
 * Java数组如何与Collection相互转换
 *
 *  Object[] toArray();
 * <T> T[] toArray(T[] var1);
 */
public class CollectionTransferArray {

    public static void main(String[] args){
        /**
         * 数组转换为集合：
         *  1、数组转换为集合可以通过Arrays这个类的asList方法来转换为ArrayList对象。
         *  注意：这个ArrayList是Arrays这个类的一个内部类，一旦用asList转换为ArrayList之后，就不能对它进行修改了（set是可以的）。
         *      因为它没有add、remove、addAll等对集合进行修改的方法。
         *  2、Collection这个类中有一个boolean addAll(Collection<? extends E> c);方法。这个参数是Collection，这意味Collection的所有
         *      实现类都有这个方法。所以我们可以通过addAll的方式把Arrays.asList转换为的ArrayList添加到相应的集合中。
         */
        String[] str = new String[10];
        for(int i=0;i<10;i++){
            str[i] = RandomString.getRamdomString(5);
        }
        System.out.println(Arrays.toString(str));
        String[] str01 = new String[20];
        for(int i=0;i<20;i++){
            str01[i] = RandomString.getRamdomString(5);
        }
        List<String> stringList = new ArrayList<String>(Arrays.asList(str));
        System.out.println(Arrays.toString(str01));
        /**
         * 集合转换为数组：
         *   Collection类中定义了这样的两个方法：
         *     Object[] toArray();
         *     <T> T[] toArray(T[] var1);
         *     通过方法名我们可以知道是转换为数组的两个方法。它们的区别是一个转换之后为Object的数组，一个转换为指定类型的数组。
         *     由于这两个方法在Collection中，所以所有的Collection的实现类都必须实现这两个方法。
         *     需要注意的是：对于<T> T[] toArray(T[] var1);这个方法，
         *          传入的数组为空数组：
         *              1、如果传入的数组是一个长度小于集合的长度，则转换之后的数组为集合中的所有元素。
         *              2、如果传入的数组长度大于集合的长度，则转换之后的数组中超出的集合的长度的部分，元素为null。
         *          传入的数组不为空数组：
         *              1、如果传入的数组是一个长度小于集合的长度，则转换之后的数组为集合中的所有元素。
         *              2、如果传入的数组长度大于集合的长度，则转换之后的数组中<=集合长度的部分为集合中的元素，接着的下一个元素为null，再
         *                  接着的下一个元素为数组中剩下的元素。
         *                          if (a.length > size)
         *                              a[size] = null;//这个是多么操蛋的设计
         */
        System.out.println(Arrays.toString(stringList.toArray(str01)));
    }
}
