/**
 * create by Administrator
 * 2019-09-19
 */
package com.bj.zl.learn.lock;

import com.bj.zl.learn.curator.CuratorClient;
import com.bj.zl.learn.zookeeper.main.dao.ZookeeperDao;
import com.bj.zl.learn.zookeeper.main.service.lockService;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CuratorLockService {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        CuratorLockService lockService = new CuratorLockService();
        lockService.runThread();
    }

    public void runThread(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        lockService service = context.getBean("lockService",lockService.class);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i=0; i<20; i++){
            executorService.submit(()->{
                try {
                    //等待倒计数完成
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //int execute = execute();
                service.loclMethod();
                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //System.out.println(execute);
            });
        }

        countDownLatch.countDown();
        executorService.shutdown();
    }
    //定义分布式锁
    /*private int execute() {
        InterProcessMutex lock = new InterProcessMutex(new CuratorClient().getClient(),"/lock");
        try {
            //获取锁
            //一直等待锁
            //lock.acquire();
            //尝试获取锁,如果在指定时间获取锁,则返回true
            if (lock.acquire(1000, TimeUnit.SECONDS)){

            }
            //Thread.sleep(50);
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                //释放锁
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }*/
}
