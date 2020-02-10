package com.bj.zl.learn.tx;

import com.bj.zl.learn.tx.service.GoodsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        GoodsService goodsService = context.getBean("goodsServiceImpl", GoodsService.class);
        int update = goodsService.updateByPrimaryKeyStore(1);
        System.out.println(update);
    }
}
