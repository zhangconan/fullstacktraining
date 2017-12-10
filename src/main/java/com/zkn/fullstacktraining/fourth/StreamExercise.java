package com.zkn.fullstacktraining.fourth;

import com.zkn.fullstacktraining.common.domain.UserInfoDomain;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Create By ZKN
 *
 * @author zhangkenan
 * @date 2017/12/10
 * @time 上午11:17
 */
public class StreamExercise {


    @Test
    public void testReduce() {

        List<UserInfoDomain> userInfoList = getUserInfoDomains();
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        //reduce的第一个方法 只有一个参数BinaryOperate 合并结果
        //如果没有元素的话，会返回false，如果有一个元素的话，不会走合并的逻辑
        userInfoList.stream().reduce((firBean, secBean) -> {
            firBean.setUserName(firBean.getUserName() + "分隔符" + secBean.getUserName());
            return firBean;
        }).ifPresent(e -> System.out.println(e.getUserName()));

        //reduce的第二个方法，有两个参数,第一个参数作为初始值，第二个参数是BinaryOperate
        System.out.println(userInfoList.stream().reduce(new UserInfoDomain(), (firBean, secBean) -> {
            firBean.setUserName(firBean.getUserName() + " == " + secBean.getUserName());
            return firBean;
        }).getUserName());

        //reduce的第三个方法，有三个参数，第一个参数为初始值，第二个参数为BiFunction，第三个参数为BinaryOperate
        System.out.println(userInfoList.stream().reduce(new UserInfoDomain(), (firBean, secBean) -> {
            if (firBean.getUserName() != null && firBean.getUserName().equals(secBean.getUserName())) {
                firBean.setPassWord(firBean.getUserName() + "##" + secBean.getUserName());
                return firBean;
            } else {
                return secBean;
            }
        }, (firBean, secBean) -> {
            firBean.setUserName(firBean.getUserName() + "@@" + secBean.getUserName());
            return firBean;
        }).getUserName());
        //只有一个参数
        List<UserInfoDomain> testUserInfo = new ArrayList<>(1);
        UserInfoDomain userInfo = new UserInfoDomain();
        userInfo.setUserName("nihao");
        testUserInfo.add(userInfo);

        testUserInfo.stream().reduce((firBean, secBean) -> {
            System.out.println(firBean.getUserName() + " " + secBean.getUserName());
            firBean.setUserName(firBean.getUserName() + " fen " + secBean.getUserName());
            return firBean;
        }).ifPresent(e -> System.out.println(e.getUserName()));
    }

    @Test
    public void testCollectorToMap() {

        List<UserInfoDomain> userInfoList = getUserInfoDomains();

        //Collectors.toMap(Function,Function) 两个参数的方法
        //会出现的问题是：有重复的key会报错
        //Bean中的某个属性做key，bean对象做value(Function.indentity())
//        userInfoList.stream().collect(Collectors.toMap(UserInfoDomain::getUserName, Function.identity())).forEach(
//                (key, value) -> System.out.println(key + " " + value)
//        );

        //Collectors.toMap(Function,Function,BiniaryOperate) 三个参数的方法
        //解决重复的key报错的
        userInfoList.stream().collect(Collectors.toMap(UserInfoDomain::getUserName, e -> e,
                (key, value) -> value)).forEach((key, value) -> System.out.println(key + " " + value));
        //Collector.toMap(Function,Function,BinaryOperate,Supplier)
        //三个参数的方法，传入一个默认的Map
        Map<String, UserInfoDomain> mapResult = new HashMap<>(8);
        mapResult.put("lisiq", new UserInfoDomain());
        userInfoList.stream().collect(Collectors.toMap(UserInfoDomain::getUserName, Function.identity(),
                (key, value) -> value, () -> mapResult)).forEach((key, value) -> System.out.println(key + " " + value));
    }

    @Test
    public void test() {
        argument();
    }

    public void argument(String... str) {
        Objects.requireNonNull(str);
        System.out.println("argument");
    }

    /**
     * 抽取结果集
     *
     * @return
     */
    private List<UserInfoDomain> getUserInfoDomains() {
        List<UserInfoDomain> userInfoList = new ArrayList<>(4);
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserName("zhangsan");
        userInfoDomain.setPassWord("woshizhangsan");
        userInfoList.add(userInfoDomain);

        userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserName("lisi");
        userInfoList.add(userInfoDomain);

        userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserName("wangwu");
        userInfoList.add(userInfoDomain);

        userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserName("zhangsan");
        userInfoDomain.setPassWord("wocaishizhangsan");
        userInfoList.add(userInfoDomain);
        return userInfoList;
    }
}

