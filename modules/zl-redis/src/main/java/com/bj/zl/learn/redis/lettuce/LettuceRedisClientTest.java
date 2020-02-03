/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.ExecutionException;

public class LettuceRedisClientTest {

    /*public static void main(String[] args) {

        //连接格式redis://[password@]host:port[/databaseNumber]其中[]中的内容可以省略
        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");

        //获取连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        //对redis操作是同步的
        //同步 即对redis操作,需要等redis的返回结果.
        RedisCommands<String, String> sync = connect.sync();
        //清空当前redis
        sync.flushdb();
        //放入数据
        System.out.println(sync.set("k1","v1"));
        //获取数据
        System.out.println(sync.get("k1"));

        //List类型
        Long size = sync.lpush("k2","1","2","3","4");
        System.out.println(size);
        //Set类型
        System.out.println(sync.sadd("set","11","22","33"));

    }*/

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //
        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
        //异步连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> async = connect.async();
        //响应式connect.reactive();

        //设置数据
        //这部是异步操作,不需要等待redis的返回结果
        RedisFuture<String> setFuturn = async.set("async", "value");
        //这步是获取redis结果,需要等待redis返回,等待返回会阻塞
        System.out.println(setFuturn.get());


        //获取数据
        RedisFuture<String> getFuturn = async.get("async");
        System.out.println(getFuturn.get());
    }


}
