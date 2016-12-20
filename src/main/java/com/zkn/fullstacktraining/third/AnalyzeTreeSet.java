package com.zkn.fullstacktraining.third;

/**
 * Created by zkn on 2016/12/20.
 * TreeSet继承了什么Set，与HashSet的区别是？HashSet与HashTable是“一脉相承”的么？
 */
public class AnalyzeTreeSet {

    /**
     * TreeSet继承了AbstractSet，实现了NavigableSet接口。
     * TreeSet和HashSet的区别：
     *      1、TreeSet的内部是用NavigableMap实现的，而HashSet的内部是用HashMap实现的。
     *      2、TreeSet中的元素是有序的。TreeSet可以通过构造函数自定义Comparator来实现排序，或者默认按照元素的自然顺序排序。
     *          HashSet内部的元素是无序的。
     *      3、TreeSet中的元素都必须实现Comparable接口（或者被指定的比较器所接受）。另外，所有这些元素都必须是可互相比较的。
     *          HashSet没有这些要求。
     *      4、TreeSet实现了NavigableSet接口，实现了方法lower、floor、ceiling和higher，它们分别返回小于、小于等于、大于等于、
     *          大于给定元素的元素，如果不存在这样的元素，则返回 null。
     *      5、TreeSet可以按升序或降序访问和遍历，而HashSet只能通过迭代器进行遍历。
     *      6、TreeSet中的方法的返回值在允许null元素的实现中可能是不确定的，因此此接口的实现中不允许插入null元素,而HashSet是没有此限制的。
     *      7、TreeSet中的descendingSet方法返回Set的一个视图，该视图表示的所有关系方法和方向方法都是逆向的。
     *            升序操作和视图的性能很可能比降序操作和视图的性能要好。TreeSet还实现了pollFirst和pollLast方法，它们返回并移除最小和最大的元素
     *          （如果存在），否则返回null。subSet、headSet和tailSet方法与名称相似的SortedSet方法的不同之处在于：
     *           可以接受用于描述是否包括（或不包括）下边界和上边界的附加参数。任何NavigableSet的子视图必须实现NavigableSet接口。
     */
    /**
     *   HashSet和HashTable是不同的两个东西。由于HashSet底层是由HashMap实现的，所以这里主要说一下HashMap和HashTable的区别。
     *      1、HashMap是线程不安全的（JDK1.5之后可以用ConcurrentHashMap来实现线程安全,它比HashTable具有更好的扩展性），Hashtable是线程安全的。
     *      2、HashMap可以接受null(HashMap可以接受为null的键值(key)和值(value)），而Hashtable则不行。
            3、HashMap的迭代器(Iterator)是fail-fast迭代器，而Hashtable的enumerator迭代器不是fail-fast的。所以当有其它线程改变了HashMap的结构（增加或者移除元素），
                将会抛出ConcurrentModificationException，但迭代器本身的remove()方法移除元素则不会抛出ConcurrentModificationException异常。但这并不是一个一定发生的行为，
                要看JVM。这条同样也是Enumeration和Iterator的区别。
            4、由于Hashtable是线程安全的也是synchronized，所以在单线程环境下它比HashMap要慢。如果你不需要同步，只需要单一线程，那么使用HashMap性能要好过Hashtable。
            5、HashMap不能保证随着时间的推移Map中的元素次序是不变的。
     */
}
