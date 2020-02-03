/**
 * create by Administrator
 * 2020-01-02
 */
package com.bj.zl.learn.redis.distributelock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedissonDistributeLockTest {


    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    }

    @Autowired
    private RedissonClient redissonClient;


    public String getLock() {
        //获取锁
        RLock rLock = redissonClient.getLock("lockName");
        //判断是当前线程的锁,并且是锁定状态
        if (rLock.isHeldByCurrentThread() && rLock.isLocked()) {
            //解锁
            rLock.unlock();
        }
        return "";
    }
}


