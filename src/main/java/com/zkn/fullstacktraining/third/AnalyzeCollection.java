package com.zkn.fullstacktraining.third;

/**
 * Created by wb-zhangkenan on 2016/12/19.
 * 分析Collection接口以及其子接口，很通俗的方式说说，
 * 究竟有哪些类型的Collection，各自解决什么样的问题
 */
public class AnalyzeCollection {

    /**
     *  Collection这个接口中大概可以分为这样三种类型的接口：List、Set、Queue(JDK1.5之后新增)。
     *  Set：
     *      1、Set是一个不包含重复元素的Collection。
     *      2、Set的底层是由Map实现的。
     *      3、Set的实现主要分为HashSet、LinkedHashSet、TreeSet三种。JDK1.5之后又新增了EnumSet及它的子类JumboEnumSet和RegularEnumSet。
     *      4、HashSet里面的元素是无序的。
     *      5、LinkedHashSet继承与HashSet。LinkedHashSet是有LinkedHashMap来实现的。LinkedHashSet中的所有操作都是在HashSet中实现的。
     *          如果创建HashSet的时候用这个构造函数：HashSet(int initialCapacity, float loadFactor, boolean dummy)(dummy没有卵用)，
     *          则创建出来的HashSet是LinkedHashS。LinkedHashSet是按照放入的元素的顺序来排序的。
     *      6、TreeSet里面的元素是有序的。TreeSet中的元素支持两种排序方式：
     *                                                          1）按照key的自然顺序排序
     *                                                          2）根据创建TreeSet时提供的Comparator实现进行排序
     *      7、TreeSet实现了NavigableSet接口。NavigableSet接口继承自SortedSet接口。
     *      8、SortedSet：插入有序Set的所有元素都必须实现Comparable接口（或者被指定的比较器所接受）。另外，所有这些元素都必须是可互相比较的。
     *      9、NavigableSet是扩展的SortedSet，具有了为给定搜索目标报告最接近匹配项的导航方法。
     *          方法lower、floor、ceiling和higher分别返回小于、小于等于、大于等于、大于给定元素的元素，如果不存在这样的元素，则返回 null。
     *      10、可以按升序或降序访问和遍历 NavigableSet。descendingSet方法返回Set的一个视图，该视图表示的所有关系方法和方向方法都是逆向的。
     *          升序操作和视图的性能很可能比降序操作和视图的性能要好。此接口还定义了pollFirst和pollLast方法，它们返回并移除最小和最大的元素
     *          （如果存在），否则返回null。subSet、headSet和tailSet方法与名称相似的SortedSet方法的不同之处在于：
     *           可以接受用于描述是否包括（或不包括）下边界和上边界的附加参数。任何NavigableSet的子视图必须实现NavigableSet接口。
     *      11、导航方法的返回值在允许null元素的实现中可能是不确定的，建议在此接口的实现中不允许插入null元素。(Comparable元素的有序集本身不允许null)
     *
     * List:
     *      1、List是一个有序的列表，可以理解为线性列表。
     *      2、List中的元素是可以重复的。
     *      3、List的主要实现类有：ArrayList、LinkedList、Vector、Stack(这个类的功能被Deque所代替)。
     *      4、AbstractList是ArrayList、LinkedList、Vector、Stack的共同父类。
     *      5、ArrayList的底层是用数组实现的。ArrayList对于查找元素比较方便，对于插入和删除性能较低，因为要用到多次的数组拷贝。
     *      6、ArrayList实现了RandomAccess接口（随机存取接口）。RandomAccess和Cloneable、Serializable一样，是一个标志性接口，不需要任何实现。
     *          只是用来表明其实现类具有某种特质的。实现了Cloneable表明可以被拷贝（浅拷贝），实现了Serializable接口表明可以被序列化，
     *          实现了RandomAccess接口则表明这个类是可以随机存取的。对我们的ArrayList来说也就标志着其数据元素之间没有关联。即两个位置相邻的元素之间没有
     *
     *      7、遍历ArrayList最好使用下标的方式进行遍历。java中的foreach语法是iterator（迭代器）的变形用法。在使用迭代器的是就需要强制建立一种互相“知晓”
     *          的关系，比如上一个元素可以判断是否有下一个元素，以及下一个元素是什么等关系。对于随机存取的ArrayList这是一个很耗时的过程。
     *      8、LinkedList是一个双向链表的结构。它内部定义了一个Node节点类，来实现双向链表。
     *      9、LinkedList实现了Deque这个接口。为add、poll提供先进先出队列操作，以及其他堆栈和双端队列操作。具有队列的特性。
     *      10、LinkedList适用于插入删除频繁的操作的情况。它的插入删除性能很高，只有改变一下前后的节点即可。
     *      11、LinkedList查找效率很低。虽然LinkedList对于查找的索引进行了二分操作，但是它仍然会有多次遍历的可能。
     *      13、Vector的底层也是用数组来实现的。
     *      14、Vector和ArrayList相比来说：Vector相对来说是线程安全的，而ArrayList是线程不安全。
     *      15、Vector在扩容的时候，如果指定了每次扩容的大小的话，则按照指定的大小进行扩容，否则扩容为原来的两倍，而ArrayList通常会扩容为原来的1.5倍。
     *      16、Stack继承自Vector。
     *      17、Stack的底层也是有数组来实现的。
     *      18、Stack类表示后进先出（LIFO）的对象堆栈。它通过五个操作对类Vector进行了扩展，允许将向量视为堆栈。它提供了通常的push和pop操作，
     *          以及取堆栈顶点的peek方法、测试堆栈是否为空的empty方法，在堆栈中查找项并确定到堆栈顶距离的search方法。
     *      19、在用到LIFO的场景下，应该优先使用Deque（可以认为Stack被淘汰了）。
     *      20、LinkedList继承了AbstractSequentialList这个类。但是感觉没有什么用。
     * Queue：
     *      1、Queue是在JDK1.5之后新增一种Collection类型。
     *      2、队列通常（但并非一定）以FIFO（先进先出）的方式排序各个元素。不过优先级队列和LIFO队列（或堆栈）例外，
     *          前者（优先队列）根据提供的比较器或元素的自然顺序对元素进行排序，后者按 LIFO（后进先出）的方式对元素进行排序。无论使用哪种排序方式，
     *          队列的头都是调用remove()或poll()所移除的元素。在FIFO队列中，所有的新元素都插入队列的末尾。其他种类的队列可能使用不同的元素放置规则。
     *          每个Queue实现必须指定其顺序属性。
     *      3、offer方法如果可插入一个元素，则返回true，否则返回false。这与Collection.add方法不同，add方法只能通过抛出未经检查的异常使添加元素失败。
     *          offer方法设计用于正常的失败情况，而不是出现异常的情况，例如在容量固定（有界）的队列中。
     *      4、remove()和poll()方法可移除和返回队列的头（remove内部是使用poll实现的）。到底从队列中移除哪个元素是队列排序策略的功能，
     *          而该策略在各种实现中是不同的。remove()和poll()方法仅在队列为空时其行为有所不同：remove()方法抛出一个异常，而poll()方法则返回null。
     *      5、element()和peek()返回队列的头，但不移除队列的头。element内部是用peek实现的。element()和peek()方法仅在队列为空时其行为有所不同：
     *          element()方法抛出一个异常，而peek()方法则返回null。
     *      6、Queue实现通常不允许插入null元素，尽管某些实现（如LinkedList）并不禁止插入null。即使在允许null的实现中，也不应该将null插入到Queue
     *          中，因为null也用作poll方法的一个特殊返回值，表明队列不包含元素。
     *      7、Queue的主要子类接口有：BlockingQueue和Deque。第一级实现类为：AbstractQueue。
     *      8、BlockingQueue接口的主要子类和接口有如下：ArrayBlockingQueue、BlockingDeque、SynchronousQueue、DelayQueue、TransferQueue、
     *          LinkedBlockingQueue、PriorityBlockingQueue.
     *      9、BlockingQueue方法以四种形式出现，对于不能立即满足但可能在将来某一时刻可以满足的操作，这四种形式的处理方式不同：第一种是抛出一个异常，
     *          第二种是返回一个特殊值（null或false，具体取决于操作），第三种是在操作可以成功前，无限期地阻塞当前线程，第四种是在放弃前只在给定的最大
     *          时间限制内阻塞。下表中总结了这些方法：
     *                          抛出异常     特殊值      阻塞     超时
     *                      插入 add(e)     offer(e)   put(e)   offer(e, time, unit)
     *                      移除 remove()   poll()     take()   poll(time, unit)
     *                      检查 element()  peek()     不可用    不可用
     *      10、BlockingQueue不接受null元素。试图add、put或offer一个null元素时，某些实现会抛出NullPointerException。
     *      11、BlockingQueue可以是限定容量的（如ArrayBlockingQueue）。它在任意给定时间都可以有一个remainingCapacity，超出此容量，
     *          便无法无阻塞地put附加元素。没有任何内部容量约束的BlockingQueue（如LinkedBlockingQueue）的可以容量为Integer.MAX_VALUE的剩余容量。
     *      12、BlockingQueue的实现主要用于生产者-使用者队列。使用remove(x)从队列中移除任意一个元素是有可能的。然而，这种操作通常不会有效执行，
     *          只能有计划地偶尔使用，比如在取消排队信息时。
     *      13、BlockingQueue的实现是线程安全的。所有排队方法都可以使用内部锁或其他形式的并发控制来自动达到它们的目的。
     *      14、ArrayBlockingQueue是一个由数组支持的有界阻塞队列（底层是由数组实现的）。此队列按FIFO（先进先出）原则对元素进行排序。队列的头部
     *          是在队列中存在时间最长的元素。队列的尾部是在队列中存在时间最短的元素。新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。
     *      15、ArrayBlockingQueue这是一个典型的“有界缓存区”，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。一旦创建了这样的缓存区，
     *          就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。注意：这里指的是put和take这两个方法。
     *      16、ArrayBlockingQueue此类支持对等待的生产者线程和使用者线程进行排序的可选公平策略。默认情况下，不保证是这种排序。然而，通过将公平性（ReentrantLock）
     *          (fairness)设置为true而构造的队列（构造函数中fair属性）允许按照FIFO顺序访问线程。公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”。
     *      17、Deque是JDK1.6之后新增的接口。Deque是一个Collection，支持在两端插入和移除元素。大多数 Deque 实现对于它们能够包含的元素数没有固定限制，
     *          但此接口既支持有容量限制的双端队列，也支持没有固定大小限制的双端队列。
     *      18、Deque接口定义在双端队列两端访问元素的方法。提供插入、移除和检查元素的方法。每种方法都存在两种形式：一种形式在操作失败时抛出异常，
     *          另一种形式返回一个特殊值（null 或 false，具体取决于操作）。插入操作的后一种形式是专为使用有容量限制的Deque实现设计的；
     *          在大多数实现中，插入操作不能失败。
     *                   第一个元素（头部）                 最后一个元素（尾部）
     *                    抛出异常       特殊值              抛出异常        特殊值
     *               插入 addFirst(e)    offerFirst(e)    addLast(e)     offerLast(e)
     *               移除 removeFirst() pollFirst()       removeLast()   pollLast()
     *               检查 getFirst()    peekFirst()       getLast()      peekLast()
     *                             Queue方法    等效Deque方法
     *                              add(e)      addLast(e)
     *                              offer(e)    offerLast(e)
     *                              remove()    removeFirst()
     *                              poll()      pollFirst()
     *                              element()   getFirst()
     *                              peek()      peekFirst()
     *                               堆栈方法    等效 Deque 方法
     *                               push(e)    addFirst(e)
     *                               pop()      removeFirst()
     *                               peek()     peekFirst()
     *      19、Deque接口提供了两种移除内部元素的方法：removeFirstOccurrence和removeLastOccurrence。与List接口不同，此接口不支持通过索引访问元素。
     *      20、Deque中最好不要插入null元素。
     *      21、ArrayDeque的底层是用数组实现的。默认是一个16个元素的数组。最大容量不能超过2^31-1.
     *      22、ArrayDeque可能在用作堆栈时快于Stack，在用作队列时快于LinkedList。
     *      23、LinkedBlockingQueue一个基于已链接节点的、范围任意的blocking queue（它是一个单向的链表）。此队列按 FIFO（先进先出）排序元素。队列的头部是在队列中时间
     *          最长的元素。队列的尾部 是在队列中时间最短的元素。新元素插入到队列的尾部，并且队列获取操作会获得位于队列头部的元素。链接队列的吞吐量通常要高于基于数组的队列。
     *      24、LinkedBlockingQueue可选的容量范围构造方法参数作为防止队列过度扩展的一种方法。如果未指定容量，则它等于Integer.MAX_VALUE。除非插入节点会使队列超出容量，
     *          否则每次插入后会动态地创建链接节点。
     *      25、LinkedBlockingQueue和LinkedBlockingQueue和像，不同的是LinkedBlockingQueue是一个双向链表。
     *      26、PriorityQueue是一个基于优先级堆的无界优先级队列。优先级队列的元素按照其自然顺序进行排序，或者根据构造队列时提供的Comparator进行排序，
     *          具体取决于所使用的构造方法。优先级队列不允许使用null元素。依靠自然顺序的优先级队列还不允许插入不可比较的对象（这样做可能导致 ClassCastException）。
     *      27、PriorityQueue如果不知道优先级队列的容量的话，默认容量为11.
     *      28、PriorityQueue此队列的头是按指定排序方式确定的最小元素。如果多个元素都是最小值，则头是其中一个元素——选择方法是任意的。队列获取操作
     *          poll、remove、peek和element访问处于队列头的元素。
     *      29、PriorityQueue内部是用数组实现的。
     *      30、PriorityQueue是线程不安全的。
     *      31、PriorityBlockingQueue是线程安全的。它的底层也是用数组实现的。可以使用方法drainTo按优先级顺序移除全部或部分元素，并将它们放在另一个collection中。
     *           其他的和PriorityQueue类似。
     *      32、ConcurrentLinkedQueue一个基于链接节点的无界线程安全队列(单向链表)。此队列按照FIFO（先进先出）原则对元素进行排序。队列的头部是队列中时间最长的元素。
     *          队列的尾部是队列中时间最短的元素。新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。当多个线程共享访问一个公共collection时，
     *          ConcurrentLinkedQueue是一个恰当的选择。此队列不允许使用null元素。具体实现暂未看懂。
     *      33、ConcurrentLinkedDeque是一个线程安全的双向链表队列。
     *      34、TransferQueue生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。新添加的transfer方法用来实现这种约束。
     *          阻塞就是发生在元素从一个线程transfer到另一个线程的过程中，它有效地实现了元素在线程之间的传递（以建立Java内存模型中的happens-before关系的方式）
     *
     *      34、SynchronousQueue一种阻塞队列，其中每个插入操作必须等待另一个线程的对应移除操作 ，反之亦然。同步队列没有任何内部容量，甚至连一个队列的容量都没有。
     *          不能在同步队列上进行peek，因为仅在试图要移除元素时，该元素才存在；除非另一个线程试图移除某个元素，否则也不能（使用任何方法）插入元素；
     *          也不能迭代队列，因为其中没有元素可用于迭代。队列的头 是尝试添加到队列中的首个已排队插入线程的元素；如果没有这样的已排队线程，
     *          则没有可用于移除的元素并且poll()将会返回null。对于其他Collection方法（例如 contains），SynchronousQueue 作为一个空
     *          collection。此队列不允许null元素。
     *     35、SynchronousQueue的队列长度为0。常用于两个线程之间传递元素。
     *     36、LinkedTransferQueue实际上是ConcurrentLinkedQueue、SynchronousQueue（公平模式）和LinkedBlockingQueue的超集。而且
     *          LinkedTransferQueue更好用，因为它不仅仅综合了这几个类的功能，同时也提供了更高效的实现。
     *
     */
}













