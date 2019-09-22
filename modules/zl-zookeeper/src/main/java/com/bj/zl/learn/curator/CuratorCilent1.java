/**
 * create by Administrator
 * 2019-09-01
 */
package com.bj.zl.learn.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.retry.RetryNTimes;

/**
 * TreeCache 监控所有节点
 * 监听器接口 TreeCacheListener
 */
public class CuratorCilent1 {


    private CuratorFramework client = null;
    private static final String ZK_ADRESS = "127.0.0.1";
    private static final String ROOT = "/tree1";

    public CuratorCilent1() {
        RetryPolicy retryPolicy = new RetryNTimes(2,1000);
        client = CuratorFrameworkFactory.newClient(ZK_ADRESS,retryPolicy);
        client.start();
    }

    public static void main(String[] args) throws Exception {

        CuratorCilent1 curatorCilent = new CuratorCilent1();
        System.out.println("连接成功..clien:" + curatorCilent.client);
        //对于指定节点下的所有节点进行监听
        TreeCache treeCache = new TreeCache(curatorCilent.client,ROOT);

        treeCache.start();

        //添加监听事件
        treeCache.getListenable().addListener((CuratorFramework client, TreeCacheEvent event) -> {
            System.out.println("Tree事件监听到了....");
            System.out.println(event.getType().name());
            if (event.getData() != null){
                System.out.println("监听的路径:"+event.getData().getPath());
                System.out.println("监听的数据:"+new String(event.getData().getData()));
                System.out.println("监听的状态:"+event.getData().getStat());
            }
        });

        Thread.sleep(10000000);
    }
}
