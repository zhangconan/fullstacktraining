package com.zkn.fullstacktraining.fourth;

import java.util.stream.Stream;

/**
 * Created by wb-zhangkenan on 2016/12/28.
 * 下面代码为什么输出流中的每个元素2遍
 */
public class StreamLearn01 {

    public static void main(String[] args){
        /**
         *  1、filter是一个中间操作，forEach是一个终止操作。中间操作只是做出某种程度的数据映射/过滤，不会真正开始遍历，
         *      终止操作才会开始遍历流。
         *  2、对于forEach、forEachOrdered在每个元素上执行，所以filter中的元素都会输出（无论filter中的条件为true或者false）。
         *  3、对于findFirst、limit、findAny、anyMatch、noneMatch,如果满足filter中的条件的话，则filter中输出满足条件的元素，
         *              如果都不满足的话，则filter中输出全部元素。
         *  4、对于allMatch(Stream中全部元素符合传入的predicate，返回true,要一个元素不满足条件，就 skip 剩下的所有元素，返回 false)
         *          如果满足filter中的条件的话，则filter中输出满足条件的元素和它的下一个元素（如果有的话），
         *  5、anyMatch、allMatch、noneMatch、findFirst、findAny、limit都是Short-circuiting方法，它们都不是要遍历全部元素才能返回结果。
         *      只要找到满足条件的（或者不满足条件的）就会skip剩下的所有元素，返回false。
         *    (filter返回false可以认为是一直找不到满足条件的元素，一直寻找，一直输出)。
         */
        Stream.of("a2","b1","d2","b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .noneMatch( s->s.startsWith("b"));
                //.ifPresent(s -> System.out.println("forEach: " + s));
                //.forEach(s -> System.out.println("forEach: " + s));
//        Optional optional = Stream.of(1,2,-4,3,4,5).filter(s -> {
//            System.out.println("filter: " + s);
//            return false;
//        }).max((int1,int2) -> int1.compareTo(int2));
//        System.out.println(optional.orElse("没有找到"));
    }
}
