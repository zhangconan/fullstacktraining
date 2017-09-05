package com.zkn.fullstacktraining.spring.two;

import com.zkn.fullstacktraining.common.domain.OrderDomain;
import com.zkn.fullstacktraining.common.domain.UserInfoDomain;
import com.zkn.fullstacktraining.spring.two.service.OrderService;
import com.zkn.fullstacktraining.spring.two.service.UserService;
import org.junit.Test;

/**
 * Created by zkn on 2017/9/5.
 */
public class ApplicationContextTest {

    @Test
    public void testApplication() throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.init("com.zkn.fullstacktraining.spring.two");
        OrderService orderService = (OrderService) applicationContext.getBean(OrderService.class);
        OrderDomain orderDomain = new OrderDomain();
        orderDomain.setType("网上购物!");
        orderService.testGeneric(orderDomain);
        
        UserService userService = (UserService) applicationContext.getBean(UserService.class);
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserName("张三");
        userService.testGeneric(userInfoDomain);
    }
}
