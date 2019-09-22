/**
 * create by Administrator
 * 2019-09-22
 */
package com.bj.zl.learn.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
/**
 * zkClient客户端实现分布式锁
 * 基于有序临时节点
 */
public class ZkClientLock {

    private ZkClient zooKeeper;
    private static final String ZK_ADDRESS = "127.0.0.1";
    //锁根节点
    private static String rootName = "/zklock";
    //锁节点
    private String lockName;
    //当前锁节点名称
    private String cuurentLockName;
    //zookeeper 超时时间
    private int sessionTimeout = 100000;

    /**
     * 创建连接
     * @param lockName
     */
    public ZkClientLock(String lockName) {
        this.lockName = lockName;

        try{
            zooKeeper = new ZkClient(ZK_ADDRESS);

            //创建一个根节点/lock
            if (!zooKeeper.exists(rootName)){
                zooKeeper.create(rootName,"rootLock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 加锁
     */
    public void lock(){

        String node = zooKeeper.create(rootName + "/" + lockName,
                "zkclient".getBytes(), CreateMode.PERSISTENT_SEQUENTIAL);

        //获取所有子节点
        List<String> subNodes = zooKeeper.getChildren(rootName);
        TreeSet<String> sortNodes = new TreeSet<>();
        for (String subNode : subNodes){
            sortNodes.add(rootName + "/" +subNode);
        }
        //获取最小节点
        String firstNode = sortNodes.first();
        //获取前一个节点
        String preNode = sortNodes.lower(node);

        if (firstNode.equals(node)){
            //则当前节点为锁节点
            cuurentLockName= node;
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        if (StringUtils.isNotBlank(preNode)){
            //说明非锁节点,需要等待,并监听前一个节点
            boolean exists = zooKeeper.exists(preNode);
            if (exists){
                //监听前一个节点
                zooKeeper.subscribeDataChanges(preNode, new IZkDataListener() {
                    @Override
                    public void handleDataChange(String dataPath, Object data) throws Exception {

                    }

                    @Override
                    public void handleDataDeleted(String dataPath) throws Exception {
                        countDownLatch.countDown();
                    }
                });

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cuurentLockName = node;
            }

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
