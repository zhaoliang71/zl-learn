/**
 * create by Administrator
 * 2019-09-20
 */
package com.bj.zl.learn.lock;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper原生客户端实现分布式锁
 * 基于有序临时节点
 */
public class ZookeeperLock {

    private ZooKeeper zooKeeper;
    private static final String ZK_ADDRESS = "127.0.0.1";
    //锁根节点
    private static String rootName = "/zklock";
    //锁节点
    private String lockName;
    //当前锁节点名称
    private String cuurentLockName;
    //zookeeper 超时时间
    private int sessionTimeout = 100000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    public ZookeeperLock(String lockName) {
        //锁节点名称 初始化
        this.lockName = lockName;
        //zookeeper初始化
        try{
            zooKeeper  = new ZooKeeper(ZK_ADDRESS, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {

                    if (event.getState() == Event.KeeperState.SyncConnected){
                        //zookeeper连接上了
                        countDownLatch.countDown();
                    }
                }
            });
            //等待zookeeper连接成功
            countDownLatch.await();

            //创建一个根节点/lock
            if (zooKeeper.exists(rootName,false) == null){
                zooKeeper.create(rootName,"rootLock".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }



        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * zookeeper加锁
     * 获取分布式锁
     */
    public void lock(){
        try {
            String node = zooKeeper.create(rootName + "/" + lockName,
                    "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            //拿到根节点下的子节点  临时有序的子节点
            List<String> children = zooKeeper.getChildren(rootName, false);
            TreeSet<String> childNodes = new TreeSet<>();
            //childNodes.addAll(children);
            for (String c : children){
                childNodes.add(rootName+ "/" +c);
            }
            //排好顺序的节点,取出最小的
            String minNode = childNodes.first();
            //获取指定节点的前一个节点
            String preNode = childNodes.lower(node);

            //判断是不是最小节点
            if (node.equals(minNode)) {
                //当前进来的这个线程锁创建的节点就是分布式锁节点
                 cuurentLockName = node;
                 return;
            }
            //用于阻塞未获取锁线程
            CountDownLatch countDownLatch = new CountDownLatch(1);


            //其他线程没拿到锁
            if (null != preNode){
                //如果前一个节点不为null 则去监听她
                Stat exists = zooKeeper.exists(preNode, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (event.getType() == Event.EventType.NodeDeleted){
                            //监听到前一个节点的删除事件,则到计数器减一
                            countDownLatch.countDown();
                        }
                    }
                });
                if (exists != null){
                    //等待获取锁
                    countDownLatch.await();
                    //当前前一个节点删除,到计数器减一之后,当前线程获取锁
                    cuurentLockName = node;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * zookeeper解锁
     * 主要做的就是删除当前锁节点删除
     */
    public void unlock(){

        try{
            if (StringUtils.isNotBlank(cuurentLockName)){
                zooKeeper.delete(cuurentLockName,-1);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
