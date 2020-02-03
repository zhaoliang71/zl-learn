/**
 * create by Administrator
 * 2020-01-12
 */
package com.bj.zl.learn.rabbitMq.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        SendMessage sendMessage = context.getBean(SendMessage.class);

        sendMessage.sendMessage("hello direct~!");
    }
}
