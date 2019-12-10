/**
 * create by Administrator
 * 2019-09-30
 */
package com.bj.zl.learn.dubbo;

import com.bj.zl.learn.contract.domain.User;
import com.bj.zl.learn.contract.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = context.getBean(UserService.class);

        User userById = userService.getUserById(1);
        System.out.println(userById);
        LOGGER.info("consumer,user:{}",userById);
    }
}
