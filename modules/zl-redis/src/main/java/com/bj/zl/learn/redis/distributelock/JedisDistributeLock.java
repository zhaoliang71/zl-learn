/**
 * create by Administrator
 * 2020-01-02
 */
package com.bj.zl.learn.redis.distributelock;

import com.bj.zl.learn.redis.jedis.pool.JedisPoolInstance;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * jedis 分布式锁实现
 * lettuce与之思路基本一致
 */
public class JedisDistributeLock {



    private static String lockPrefix = "redis:lock";
    private static AtomicBoolean flag = new AtomicBoolean(true);
    private static Long lockTimeOut = 30000L;
    static {
        //redis采用setnx与过期时间来做分布式锁,有过期时间,是因为redis是第三方软件,如果因为网络抖动等其他原因导致,手动释放锁的时候失败,则将导致缓存一直存在,而客户端再也不能获取到锁.
        //为了以上原因,所以需要用过期时间.
        //但是因为用了过期时间,会导致,如果业务代码没有走完,锁就自动释放了,则会导致线程不安全.
        //为了解决上面的问题,则需要时刻检测锁的过期时间.当过期时间较小时则恢复过期时间.虽然不能自动释放锁.但如果手动释放锁失败.
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(()-> {
            Jedis jedis = JedisPoolInstance.getInstance().getResource();
            Set<String> keys = jedis.keys(lockPrefix+"*");
            keys.forEach((String key) ->{
                Long expireTime = jedis.ttl(key);

                if (flag.get() && expireTime >= -1 && expireTime <= (lockTimeOut - 10000)/1000){
                    jedis.pexpire(key,lockTimeOut);
                }
            });
        },5,3,TimeUnit.SECONDS);
    }

    /**
     * 获取锁
     * @param lockName 锁名称
     * @param acquireTimeOut 获取锁的超时时间
     * @param lockTimeOut 锁的过期时间
     * @return
     */
    public String getLock(String lockName,Long acquireTimeOut,Long lockTimeOut){
        JedisDistributeLock.lockTimeOut = lockTimeOut;
        String lock = lockPrefix + lockName;
        String uniqueValue = UUID.randomUUID().toString();
        //获取Jedis
        Jedis jedis = JedisPoolInstance.getInstance().getResource();
        try{
            //尝试获取锁的时间
            Long end = System.currentTimeMillis() + acquireTimeOut;

            while (System.currentTimeMillis() < end){
                //设置key 和设置key的过期时间是两步,不是原子操作,有可能会出现死锁问题,需要使用lua脚本解决
                if (jedis.setnx(lock,uniqueValue) > 0){
                    //pexpire 与 expire类似, pexpire传入的毫秒  expire类似是秒
                    jedis.pexpire(lock,lockTimeOut);
                    return lock;
                }

                if (jedis.ttl(lock) == -1){
                    //lock 设置过期时间失败,重新设置
                    jedis.pexpire(lock,lockTimeOut);
                }
                //立刻马上去循环再获取锁，其实不是很好，最好是稍等片刻再去重试获取锁
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){

        }finally {
            jedis.close();
        }

        return null;
    }
    /**
     * 释放redis锁
     *
     * @param lockName
     * @param uniqueValue
     */
    public void releaseLock(String lockName,String uniqueValue){
        String lock = lockPrefix + lockName;
        Jedis jedis = JedisPoolInstance.getInstance().getResource();
        try{//判断下 锁的value是否是自己存入的..保证解锁解掉的是自己的锁
            if (jedis.get(lock).equals(uniqueValue)){
                jedis.del(lock);
            }
        }catch (Exception e){

        }finally {
            flag.set(false);
            if (jedis != null){
                jedis.close();
            }
        }

    }
}
