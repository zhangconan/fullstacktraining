package com.zkn.fullstacktraining.fourth;

/**
 * Created by wb-zhangkenan on 2016/12/26.
 * 说明Stream与Collection的区别 以及关系
 */
public class StreamAndCollection {

    /**
     * Stream不是集合元素，它不是数据结构，它也不保存数据，它是有关算法和计算的。它更像一个高级版本的Iterator。它和原始的Iterator的区别是：
     *  原始的Iterator只能显示地一个一个遍历元素并对其执行某些操作，而Stream，用户只需要给出对其包含的元素执行什么操作（例如：过滤掉年龄小于5的
     *  值，获取字符串的首字母等），Stream会隐式地在内部进行遍历，做出相应的数据转换。
     *  Stream只能单向、不可重复的执行、数据只能遍历一次，遍历过一次之后就结束了（返回新的Stream对象）。就像管子里的水，流过之后就没了。
     *  Stream可以并行化的操作。（Iterator只能命令式的，串行化的操作，即每个item对完后再读下一个。）使用并行遍历时，数据会被切分成多段，其中每一个
     *  都在不同的线程中处理，然后将结果一起输出。
     *  Stream的并行操作依赖于java7中引入的Fork/Join框架来拆分任务和加速处理过程。
     *  Stream是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作，或者大批量的数据操作。它极大的提高了编程
     *      效率和程序可读性。
     *  Stream的另外一个特点是：数据源本身可以是无限的。
     *  Stream不支持索引访问。
     *  Stream的操作必须以lambda表达式为参数
     *  Stream绝不修改自己所封装的底层数据结构的数据。
     *  Stream很容易生成数组或者List。
     *  Stream中Intermediate的操作永远是惰性化的。
     *      Intermediate：其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。仅仅调用到这类方法，并没有真正开始流的遍历。
     *          map(mapToInt,flatMap 等)、filter、distinct、sorted、peek、limit、skip、parallel、sequential、unordered都属于Intermediate。
     *      Terminal：一个流只能有一个terminal操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
     *          Terminal操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个side effect。
     *          forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator等
     *      short-circuiting：
     *           对于一个intermediate操作，如果它接受的是一个无限大（infinite/unbounded）的Stream，但返回一个有限的新Stream。
     *           对于一个terminal操作，如果它接受的是一个无限大的Stream，但能在有限的时间计算出结果。
     *           anyMatch、allMatch、 noneMatch、findFirst、findAny、limit属于short-circuiting。
     *  Collection提供了两种方式来生成一个Stream：
     *      Collection.stream() （串行）
     *      Collection.parallelStream()（并行化的操作）
     */
}
