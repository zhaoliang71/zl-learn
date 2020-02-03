/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * jedis 对 key的操作
 */
public class Jedis02 {

    public static void main(String[] args) {
        //根据端口和IP建立redis连接
        Jedis jedis = new Jedis("127.0.0.1",6379);

        //列出所有的key
        //keys命令
        Set<String> set = jedis.keys("*");
        set.forEach((key)->{
            System.out.println(key);
        });
        //检查某个key是否存在
        //exists命令 true 表示存在 false表示不存在
        System.out.println(jedis.exists("key7"));

        //将当前库的key移动到给定的库db
        //move命令 移动失败返回0 移动成功返回1
        System.out.println(jedis.move("key1",1));
        //设置key过期时间
        //expire命令 设置失败返回0 设置成功返回1
        System.out.println(jedis.expire("key2",10));
        //查看key的过期时间
        //ttl命令  -2 表示不存在key,-1表示永久不过期,正整数表示还有这些秒过期
        System.out.println(jedis.ttl("key2"));
        //查看key的值的类型
        //type命令
        System.out.println(jedis.type("key"));
        //删除key
        //del命令 删除失败返回0 删除成功返回1
        System.out.println(jedis.del("key3"));
        //关闭连接
    }

}
