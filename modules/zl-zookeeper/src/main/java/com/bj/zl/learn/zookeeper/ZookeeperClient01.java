/**
 * create by Administrator
 * 2019-08-26
 */
package com.bj.zl.learn.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperClient01 {

    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    private static final String ZNODE = "/ZNODE";
    //到计数器,默认倒数1下
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //创建zookeeper客户端对象
        ZooKeeper zooKeeper = new ZooKeeper(ZK_ADDRESS, 5000, new Watcher() {
            /**
             * 观察与zookeeper有没有连接上
             * 这是个异步操作,非主线程执行
             * @param watchedEvent
             */
            @Override
            public void process(WatchedEvent watchedEvent) {
                //获取事件的状态
                Event.KeeperState state = watchedEvent.getState();

                //获取事件类型
                Event.EventType type= watchedEvent.getType();
                //校验连接状态和类型,连接上的状态为SyncConnected,类型为None
                if (Event.KeeperState.SyncConnected == state){
                    if (Event.EventType.None == type){
                        System.out.println("连接成功");
                        countDownLatch.countDown();
                    }
                }
            }
        });
        // 倒数计数器没有倒数完成,不能执行下面的代码.确保zookeeper对象创建成功
        countDownLatch.await();

        //开始操作zookeeper

        //创建节点
        //参数 为 节点,数据,访问控制类型,创建类型(持久节点,临时节点,有序无序)
        //返回值为节点路径 /ZNODE
        String path = zooKeeper.create(ZNODE,"zookeeper".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("节点创建结果:"+path);
    }
}