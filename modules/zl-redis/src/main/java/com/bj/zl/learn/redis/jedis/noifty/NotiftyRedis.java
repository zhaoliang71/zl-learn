/**
 * create by Administrator
 * 2020-01-02
 */
package com.bj.zl.learn.redis.jedis.noifty;

import com.bj.zl.learn.redis.jedis.pool.JedisPoolInstance;
import redis.clients.jedis.Jedis;

public class NotiftyRedis {

    public static void main(String[] args) {
        Jedis jedis = JedisPoolInstance.getInstance().getResource();
        //订阅通知,NotifyListener继承JedisPubSub,实现接收通知之后的操作
        jedis.psubscribe(new NotifyListener(),"_keyspace@0__:k",
                "__keyevent@0__:del","__keyevent@0__:expire");

        jedis.setex("key",10,"d");
    }
}
