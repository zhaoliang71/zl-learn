/**
 * create by Administrator
 * 2020-01-02
 */
package com.bj.zl.learn.redis.jedis.pool;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolInstance {


    //redis服务器的ip地址
    private static final String HOST = "127.0.0.1";

    //redis服务器的端口
    private static final int PORT = 6379;

    private static final String PASSWORD = "123456";

    private static final int TIMEOUT = 10000;

    //redis连接池对象，单例的连接池对象
    private static JedisPool jedisPool = null;
    //私有构造方法
    private JedisPoolInstance() {
    }
    /**
     * 获取线程池实例对象
     *
     * @return
     */
    public static JedisPool getInstance(){
        //双重检测锁
        if (jedisPool == null){
            synchronized (JedisPoolInstance.class){
                if (jedisPool == null){
                    //对连接池的参数进行配置，根据项目的实际情况配置这些参数
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(1000);//最大连接数
                    poolConfig.setMaxIdle(32);//最大空闲连接数
                    poolConfig.setMaxWaitMillis(90*1000);//获取连接时的最大等待毫秒数
                    poolConfig.setTestOnBorrow(true);//在获取连接的时候检查连接有效性

                    jedisPool = new JedisPool(poolConfig, HOST, PORT, TIMEOUT);

                }
            }
        }
        return jedisPool;
    }
}
