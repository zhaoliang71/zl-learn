/**
 * create by Administrator
 * 2019-08-26
 */
package com.bj.zl.learn.zkclient;


import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZkClientDemo {

    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    private static final String ZNODE = "/zkclient";
    public static void main(String[] args) {
        //创建zookeeper连接
        ZkClient client = new ZkClient(ZK_ADDRESS);
        //可以重写序列化器,并在创建连接的时候指定序列化器
        ZkClient mySerializerClient = new ZkClient(ZK_ADDRESS,5000,15000,new MyZkSerializer());
        //判断节点是否存在
        if (!client.exists(ZNODE)){
            //创建持久化节点 ,初始化数据
            client.createPersistent(ZNODE,"zkclient");
            String chlid = client.create(ZNODE + "/chlid", "chlid", CreateMode.PERSISTENT);
            System.out.println(chlid);
        }else {
            //修改节点数据,并返回该节点的状态
            Stat znodeStat = client.writeDataReturnStat(ZNODE, "znode", -1);
            System.out.println(znodeStat);
        }
        //获取子节点  子节点:chlid
        List<String> children = client.getChildren(ZNODE);
        children.forEach((String node) -> {
            System.out.println("子节点:" + node);
        });
        //获取节点数据
        String readData = client.readData(ZNODE);
        Stat stat = new Stat();
        //获取节点数据时更新节点状态
        client.readData(ZNODE + "/chlid",stat);
        System.out.println(readData);
    }
}
