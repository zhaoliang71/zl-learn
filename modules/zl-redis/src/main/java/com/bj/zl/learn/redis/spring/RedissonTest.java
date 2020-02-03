/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.spring;

import com.google.common.collect.Lists;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class RedissonTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        RedissonClient redissonClient = context.getBean(RedissonClient.class);

        //操作redis
        //set命令
        redissonClient.getBucket("kk1").set("value");
        //get命令
        System.out.println(redissonClient.getBucket("kk1").get());
        ArrayList<String> strings = Lists.newArrayList("1", "2", "3");
        //List类型
        boolean kk2 = redissonClient.getList("kk2").addAll(strings);
        System.out.println("保存list 成功:"+kk2);

        redissonClient.getList("kk2").readAll().forEach((obj)->{
            System.out.println(obj);
        });

        redissonClient.shutdown();

    }
}
