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
     *          基于哈希表的Map接口的实现。此实现提供所有可选的映射操作，并允许使用null值和null键。（除了非同步和允许使用null之外，HashMap类与Hashtable大致相同。）
     *          HashMap的实例有两个参数影响其性能：初始容量和加载因子。容量是哈希表中桶的数量，初始容量只是哈希表在创建时的容量。加载因子是哈希表在其容量自动增加
     *          之前可以达到多满的一种尺度。当哈希表中的条目数超出了加载因子与当前容量的乘积时，则要对该哈希表进行rehash操作（即重建内部数据结构），从而哈希表将具有大约两倍的桶数。
     *          如果初始容量大于最大条目数除以加载因子，则不会发生rehash操作
     *      ConcurrentHashMap：
     *          支持获取的完全并发和更新的所期望可调整并发的哈希表。此类遵守与Hashtable相同的功能规范，并且包括对应于Hashtable的每个方法的方法版本。
     *          不过，尽管所有操作都是线程安全的，但获取操作不必锁定，并且不支持以某种防止所有访问的方式锁定整个表。此类可以通过程序完全与Hashtable进行互操作，
     *          这取决于其线程安全，而与其同步细节无关。
     *          获取操作（包括 get）通常不会受阻塞。
     *      HashTable:
     *          Hashtable是线程安全的.
     *          Hashtable的键值不能为空。
     *          Hashtable的enumerator迭代器不是fail-fast的.(这里可以写两个线程进行测试一下，一个线程用来循环，一个线程用来put元素).
     *      LinkedHashMap:
     *          Map接口的哈希表和链接列表实现，具有可预知的迭代顺序。此实现与HashMap的不同之处在于，后者维护着一个运行于所有条目的双重链接列表。
     *          此链接列表定义了迭代顺序，该迭代顺序通常就是将键插入到映射中的顺序（插入顺序）。注意，如果在映射中重新插入 键，则插入顺序不受影响。
     *          （如果在调用 m.put(k, v) 前 m.containsKey(k) 返回了true，则调用时会将键k重新插入到映射m中。）
     *      EnumMap:
     *          与枚举类型键一起使用的专用Map实现。枚举映射中所有键都必须来自单个枚举类型，该枚举类型在创建映射时显式或隐式地指定。枚举映射在内部表示
     *          为数组。此表示形式非常紧凑且高效。枚举映射根据其键的自然顺序来维护（该顺序是声明枚举常量的顺序）。
     *      WeakHashMap:
     *
     *      IdentityHashMap:
     *          此类利用哈希表实现 Map 接口，比较键（和值）时使用引用相等性代替对象相等性。换句话说，在IdentityHashMap中，当且仅当 (k1==k2)时，
     *          才认为两个键k1和k2相等（在正常Map实现（如 HashMap）中，当且仅当满足下列条件时才认为两个键k1和k2相等：(k1==null ? k2==null : e1.equals(e2))）。
     *          此类不是通用Map实现！此类实现Map接口时，它有意违反Map的常规协定(该协定在比较对象时强制使用equals方法)。此类设计仅用于其中需要引用
     *          相等性语义的罕见情况。
     *          此类的典型用法是拓扑保留对象图形转换，如序列化或深层复制。
     *          此类的另一种典型用法是维护代理对象。例如，调试设施可能希望为正在调试程序中的每个对象维护代理对象。
     *          此类提供所有的可选映射操作，并且允许 null 值和 null 键。此类对映射的顺序不提供任何保证；特别是不保证顺序随时间的推移保持不变。
     *      Bindings:
     *          key都是String的类。
     *      Properties：
     *          Properties 类表示了一个持久的属性集。Properties可保存在流中或从流中加载。属性列表中每个键及其对应值都是一个字符串。
     *
     *
     */
}
