/**
 * create by Administrator
 * 2020-01-02
 */
package com.bj.zl.learn.redis.jedis.noifty;

import redis.clients.jedis.JedisPubSub;

public class NotifyListener extends JedisPubSub {

    /**
     *
     * @param pattern
     * @param channel
     * @param message
     */
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("收到了个通知"+pattern + "---" + channel + "---" +message);
    }
}
