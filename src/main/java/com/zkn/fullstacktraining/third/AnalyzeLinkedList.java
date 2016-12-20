package com.zkn.fullstacktraining.third;

/**
 * Created by zkn on 2016/12/21.
 * LinkedList也是一种Queue么？是否是双向链表?
 */
public class AnalyzeLinkedList {

    /**
     * 1、LinkedList可以认为是一种Queue。因为LinkedList实现了Deque这个接口，而Deque这个接口继承了Queue这个接口。
     * 2、LinkedList是双向链表。LinkedList内部使用一个内部类实现的Node类。从Node这个类的定义上我们可以看到它是一个双向链表：
     *     private static class Node<E> {
     *                   E item;
     *                   Node<E> next;
     *                   Node<E> prev;
     *
     *                   Node(Node<E> prev, E element, Node<E> next) {
     *                       this.item = element;
     *                       this.next = next;
     *                       this.prev = prev;
     *                   }
     *                }
     *  并且有两个属性表示头和尾：
     *   transient Node<E> first;
     *   transient Node<E> last;
     * 3、LinkedList实现了Deque和Queue中的方法。
     *
     */
}
