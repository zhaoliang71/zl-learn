/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.spring;

import com.bj.zl.learn.redis.spring.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

public class JedisSpringClient {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        RedisTemplate<String,Serializable> bean = context.getBean(RedisTemplate.class);
        bean.setKeySerializer(new StringRedisSerializer());
        User user = new User();
        user.setName("jack");
        user.setAge(45);

        //字符串
        bean.opsForValue().set("keyspring",user);

        //List
        bean.opsForList().leftPush("keyList",user);

        //set
        bean.opsForSet().add("keySet",user);
        //zset
        bean.opsForZSet().add("keyZset",1,100);
        //hash
        //bean.opsForHash();

        //BoundXXXOperations操作redis
        //字符串类型
        bean.boundValueOps("skey").set(user);
        User u = (User) bean.boundValueOps("skey").get();
        System.out.println(u);

        //list
        //bean.boundListOps("list");
        //set
        //bean.boundSetOps("set");
        //zset
        //bean.boundZSetOps("zset");
        //hash
        //bean.boundHashOps("hash");
    }
}
