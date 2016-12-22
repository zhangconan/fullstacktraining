package com.zkn.fullstacktraining.third;

/**
 * Created by zkn on 2016/12/21.
 * Map的一级子接口有哪些种类，分别用作什么目的？
 */
public class AnalyzeMap {
    /**
     * Map：
     *  1、Map的一级接口大概有这三个：SortedMap、ConcurrentMap(1.5)、和Bindings(1.6).
     *      SortedMap:
     *          1)、SortedMap的直接接口有NavigableMap(1.6)、间接接口有ConcurrentNavigableMap(1.6)。主要实现类有TreeMap、
     *              ConcurrentSkipListMap(1.6)。
     *          2）、SortedMap是一个提供对键值进行排序的接口。排序规则是根据其键的自然顺序进行排序的，或者根据在创建有序映射时提供的Comparator
     *              进行排序。
     *          3）、SortedMap中插入的所有键都必须实现Comparable接口（或者被指定的比较器接受）。所有这些键都必须是可互相比较的：对有序映射中的
     *              任意两个键k1和k2执行k1.compareTo(k2)（或 comparator.compare(k1, k2)）都不得抛出ClassCastException。试图违反此
     *              限制将导致违反规则的方法或者构造方法调用抛出ClassCastException。
     *          4）、SortedMap的所有实现类都建议提供4个“标准”构造方法：1:void（无参数）构造方法，它创建一个空的有序映射，按照键的自然顺序进行排序。
     *              2:带有一个Comparator类型参数的构造方法，它创建一个空的有序映射，根据指定的比较器进行排序。3:带有一个Map类型参数的构造方法，
     *              它创建一个新的有序映射，其键-值映射关系与参数相同，按照键的自然顺序进行排序。4)带有一个 SortedMap类型参数的构造方法,它创建一个
     *              新的有序映射，其键-值映射关系和排序方法与输入的有序映射相同。
     *          5）、subMap(K fromKey,K toKey)方法：
     *                  返回此映射的部分视图，其键值的范围从fromKey（包括）到toKey（不包括）。（如果fromKey和toKey相等，则返回映射为空。）
     *                  返回的映射受此映射支持，所以在返回映射中的更改将反映在此映射中，反之亦然。返回的映射支持此映射支持的所有可选映射操作。
     *                  如果试图在返回映射的范围之外插入键，则返回的映射将抛出 IllegalArgumentException。
     *          6）、headMap(K toKey)方法：
     *                  返回此映射的部分视图，其键值严格小于toKey。返回的映射受此映射支持，所以在返回映射中的更改将反映在映射中，反之亦然。
     *                  返回的映射支持此映射支持的所有可选映射操作。如果试图在返回映射的范围之外插入键，则返回的映射将抛出IllegalArgumentException。
     *          7)、tailMap(K fromKey)方法：
     *                  返回此映射的部分视图，其键大于等于fromKey。返回的映射受此映射支持，所以在返回映射中的更改将反映在映射中，反之亦然。
     *                  返回的映射支持此映射支持的所有可选映射操作。如果试图在返回映射的范围之外插入键，则返回的映射将抛出 IllegalArgumentException。
     *          8）、firstKey()方法：
     *                  返回此映射中当前第一个（最低）键。
     *          9）、lastKey()方法：
     *                  返回映射中当前最后一个（最高）键。
     *          10）、keySet()方法：
     *                  返回在此映射中所包含键的Set视图。该Set的迭代器将按升序返回这些键。该Set受映射支持，所以对映射的更改将反映在此Set中，
     *                  反之亦然。如果对该Set进行迭代的同时修改了映射（通过迭代器自己的remove操作除外），则迭代结果是不确定的。此Set支持元素移除，
     *                  通过 Iterator.remove、Set.remove、removeAll、retainAll和clear操作可从映射中移除相应的映射关系。它不支持add或addAll操作。
     *          11)、values()方法：
     *                  返回在此映射中所包含值的Collection视图。该collection的迭代器将按对应键的升序返回这些值。该collection受映射支持，
     *                  所以对映射的更改将反映在此collection中，反之亦然。如果对该set进行迭代的同时修改了映射（通过迭代器自己的remove操作除外），
     *                  则迭代结果是不确定的。该collection支持元素的移除，通过 Iterator.remove、Collection.remove、removeAll、retainAll
     *                  和clear操作可从映射中移除相应的映射关系。它不支持 add 或 addAll 操作。
     *          12）、entrySet()方法：
     *                  返回在此映射中包含的映射关系的 Set 视图。该 set 的迭代器将按升序键顺序返回这些条目。该 set 受映射支持，
     *                  所以对映射的更改将反映在此 set 中，反之亦然。如果修改映射的同时正在对该 set 进行迭代（除了通过迭代器自己的remove操作，
     *                  或者通过在迭代器返回的映射项上执行 setValue 操作外），则迭代结果是不确定的。此 set 支持元素移除，通过 Iterator.remove、
     *                  Set.remove、removeAll、retainAll 和 clear 操作可从映射中移除相应的映射关系。它不支持 add 或 addAll 操作。
     *      NavigableMap：
     *          NavigableMap是扩展的SortedMap，具有了针对给定搜索目标返回最接近匹配项的导航方法。方法lowerEntry、floorEntry、ceilingEntry和
     *          higherEntry 分别返回小于、小于等于、大于等于、大于给定键的键的最大键关联的 Map.Entry对象，如果不存在这样的键，则返回 null。类似地，
     *          方法 lowerKey、floorKey、ceilingKey 和 higherKey 只返回小于、小于等于、大于等于、大于给定键的键的最大键关联的键(Key)。所有这些方法是为查找条目而不是遍历条目而设计的。
     *          可以按照键的升序或降序访问和遍历NavigableMap。descendingMap方法返回映射的一个视图，该视图表示的所有关系方法和方向方法都是逆向的。
     *          升序操作和视图的性能很可能比降序操作和视图的性能要好。subMap、headMap 和 tailMap 方法与名称相似的 SortedMap方法的不同之处在于：
     *          可以接受用于描述是否包括（或不包括）下边界和上边界的附加参数。任何 NavigableMap 的 Submap 必须实现 NavigableMap 接口。
     *          此接口还定义了firstEntry、pollFirstEntry、lastEntry 和 pollLastEntry方法，它们返回和/或移除最小和最大的映射关系（如果存在），否则返回 null。
     *          条目返回方法的实现应当返回 Map.Entry 对，表示产生映射关系时它们的快照，因此通常不支持可选的 Entry.setValue方法。不过要注意的是，
     *          可以使用put方法在关联映射中更改映射关系。
     *          subMap(K, K)、headMap(K)和tailMap(K)方法被指定为返回SortedMap，以允许现有SortedMap实现能相容地改进为实现NavigableMap，
     *          但鼓励此接口的扩展和实现重写这些方法以返回 NavigableMap。类似地，可以重写 SortedMap.keySet() 以返回 NavigableSet。
     *          1）、subMap(K fromKey,boolean fromInclusive,K toKey,boolean toInclusive)方法：
     *              此方法是对subMap(K fromKey,K toKey)方法的一个重载。
     *              返回此映射的部分视图，其键的范围从fromKey到toKey。如果fromKey和toKey相等，则返回的映射为空，除非fromExclusive和toExclusive
     *              都为true。返回的映射受此映射支持，因此返回映射中的更改将反映在此映射中，反之亦然。返回的映射支持此映射支持的所有可选映射操作。
     *              如果试图在返回映射的范围之外插入一个键，或者构造一个任一端点位于其范围之外的子映射，则返回的映射将抛出 IllegalArgumentException。
     *              fromKey和toKey不能为null
     *          2)、headMap(K toKey,boolean inclusive)方法：
     *              返回此映射的部分视图，其键小于（或等于，如果inclusive为true）toKey。返回的映射受此映射支持，因此返回映射中的更改将反映在此映射中，
     *              反之亦然。返回的映射支持此映射支持的所有可选映射操作。如果试图在返回映射的范围之外插入一个键，则返回的映射将抛出 IllegalArgumentException
     *          3）、tailMap(K fromKey,boolean inclusive)方法：
     *              返回此映射的部分视图，其键大于（或等于，如果 inclusive 为 true）fromKey。返回的映射受此映射支持，因此返回映射中的更改将反映在此映射中，
     *              反之亦然。返回的映射支持此映射支持的所有可选映射操作。如果试图在返回映射的范围之外插入一个键，则返回的映射将抛出 IllegalArgumentException。
     *
     *      ConcurrentNavigableMap：
     *          线程安全的导航Map。
     *      TreeMap:
     *          基于红黑树（Red-Black tree）的 NavigableMap实现。该映射根据其键的自然顺序进行排序，或者根据创建映射时提供的 Comparator 进行排序，
     *          具体取决于使用的构造方法。
     *          快速失败的：iterator 方法返回的迭代器都是快速失败的：在迭代器创建之后，如果从结构上对映射进行修改，除非通过迭代器自身的 remove 方法，
     *              否则在其他任何时间以任何方式进行修改都将导致迭代器抛出 ConcurrentModificationException。
     *      ConcurrentSkipListMap：
     *          跳表的Map（后续再补）。
     *      ConcurrentMap：
     *          线程安全的Map。
     *      HashMap:
     *
     *
     *
     *
     */
}
