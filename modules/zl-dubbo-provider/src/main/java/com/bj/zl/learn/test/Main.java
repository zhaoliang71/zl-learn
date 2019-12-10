/**
 * create by Administrator
 * 2019-09-30
 */
package com.bj.zl.learn.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        //让spring容器不退出
        try {
            //等待输入
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
