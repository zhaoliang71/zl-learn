/**
 * create by Administrator
 * 2019-09-22
 */
package com.bj.zl.learn.zookeeper.main.service;

import com.bj.zl.learn.curator.CuratorClient;
import com.bj.zl.learn.lock.CuratorLockService;
import com.bj.zl.learn.lock.ZkClientLock;
import com.bj.zl.learn.lock.ZookeeperLock;
import com.bj.zl.learn.zookeeper.main.dao.ZookeeperDao;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Service("lockService")
public class lockService {
    @Autowired
    private ZookeeperDao dao;
    //InterProcessMutex lock = new InterProcessMutex(new CuratorClient().getClient(),"/lock");
    //private ZookeeperLock lock = new ZookeeperLock("zkLock");
    private ZkClientLock lock = new ZkClientLock("zkLock");
    public  void loclMethod(){

        try {
            //获取锁
            //一直等待锁
            //lock.acquire();
            //尝试获取锁,如果在指定时间获取锁,则返回true
            /*if (lock.acquire(1000, TimeUnit.SECONDS)){
                int check = dao.check();
                if (check > 0){
                    System.out.println("售出");
                    dao.des(--check);
                }else {
                    System.out.println("没有库存");
                }
            }*/

            //zookeeper 锁
            lock.lock();
            int check = dao.check();
            if (check > 0){
                System.out.println("售出");
                dao.des(--check);
            }else {
                System.out.println("没有库存");
            }
            //Thread.sleep(50);
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                //释放锁
                //lock.release();
                lock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
