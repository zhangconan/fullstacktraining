package com.zkn.fullstacktraining.fourth;

/**
 * Created by wb-zhangkenan on 2016/12/27.
 */
public class LambdaLearn01 {

    /**
     * Predicate<T>——接收T对象并返回boolean
     * UnaryOperator<T>——接收T对象，返回T对象
     * BiFunction<T, U, R>——接收两个对象(T,U)，返回R对象
     * Function<T, R>——接收T对象，返回R对象
     * Consumer<T>——接收T对象，不返回值
     * Supplier<T>——提供T对象（例如工厂），不接收值
     * Supplier<A> supplier();//就是生成容器（A容器）
     * BiConsumer<A, T> accumulator();//是往A容器添加元素T
     * BinaryOperator<A> combiner();//合并容器（多个A容器合并）
     * Function<A, R> finisher();//转换容器输出（可选从A容器改为R容器）
     * Set< > characteristics();（特性说明）
     */
}
