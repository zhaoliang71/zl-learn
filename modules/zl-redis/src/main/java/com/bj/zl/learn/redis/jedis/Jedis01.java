/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * jedis一个命令对应一个Java方法
 */
public class Jedis01 {

    public static void main(String[] args) {
        //创建Jedis连接
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //如果设置了密码
        //jedis.auth("123456");
        System.out.println("ping一下redis服务:"+jedis.ping());
        //字符串操作
        jedis.del("key");
        //set命令
        jedis.set("key","1");
        //get命令
        System.out.println(jedis.get("key"));
        //incr自增 value 必须是数字
        System.out.println(jedis.incr("key"));
        //decr自减 value 必须是数字
        System.out.println(jedis.decr("key"));
        //setnx如果存在就不设置,不存在就设置,不抛出异常
        //设置成功返回1
        //设置失败返回0
        System.out.println(jedis.setnx("key","value1"));
        System.out.println(jedis.setnx("key1","value1"));
        //setex 设置key时,添加过期时间,10秒过期
        jedis.setex("key",10,"v");
        //关闭连接
        jedis.close();

    }
}
