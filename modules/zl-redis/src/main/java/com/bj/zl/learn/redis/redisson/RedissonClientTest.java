/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.redisson;

import com.google.common.collect.Lists;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;

public class RedissonClientTest {

    public static void main(String[] args) {
        //连接redis配置对象
        Config config = new Config();
        //单机版
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");//.setPassword("123456");

        //创建Redisson对象
        RedissonClient redissonClient = Redisson.create(config);

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


        //set类型
        redissonClient.getSet("kk3").addAll(strings);

        //zset
        redissonClient.getScoredSortedSet("kk4").add(100,"df");

        //hash
        redissonClient.getMap("kk5").put("name","value");
        //不关闭 程序不会停止
        redissonClient.shutdown();
    }
}
