/**
 * create by Administrator
 * 2019-08-20
 */
package com.bj.zl.learn.curator;


import javafx.scene.Node;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

import java.util.List;
import java.util.function.Consumer;

public class CuratorClient {

    private static final String ZK_ADDRESS = "127.0.0.1";

    private static final String ROOT_NODE = "/curator";

    private CuratorFramework client = null;

    public CuratorClient() {
        //重试策略,重试3次,间隔2S
        RetryPolicy retryPolicy = new RetryNTimes(3,2000);
        //创建zookeeper连接
        client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, retryPolicy);

        //开启客户端
        client.start();
    }

    public static void main(String[] args) throws Exception {
        CuratorClient curatorClient = new CuratorClient();
        System.out.println("zookeeper连接成功client:" + curatorClient.client);

        //查看连接状态 LATENT  未start, STARTED  start了
        System.out.println(curatorClient.client.getState().name());

        //创建节点 curator流式的编码方式  creatingParentsIfNeeded如果需要把父节点也创建
       /* String node = curatorClient.client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(ROOT_NODE + "/child","child".getBytes());
*/
       // System.out.println(node);
        //更新节点
      //  Stat stat = curatorClient.client.setData().withVersion(-1).forPath(ROOT_NODE, "curator".getBytes());
      //  System.out.println(stat);

        //读取数据 将数据存在stat里
      //  Stat dataStat = new Stat();
       // byte[] bytes = curatorClient.client.getData().storingStatIn(dataStat).forPath(ROOT_NODE);
       // System.out.println(new String(bytes));
       // System.out.println(dataStat);

        //curatorClient.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(ROOT_NODE + "/child1","child1".getBytes());
        //查询节点
        //List<String> childStrList = curatorClient.client.getChildren().forPath(ROOT_NODE);

        /**
         * forEach方法
         *
         * default void forEach(Consumer<? super T> action) {
         *      Objects.requireNonNull(action);
         *      for (T t : this) {
         *          action.accept(t);
         *      }
         *  }
         *
         *
         *  Consumer对象 函数式接口
         *  如何表达函数式接口
         *  1.匿名内部类
         *   childStrList.forEach(new Consumer<String>() {
         *      @Override
         *      public void accept(String s) {
         *           System.out.println(s);
         *       }
         *   });
         *   2.lambda表达式
         *    childStrList.forEach((String child) -> {
         *      System.out.println(child);
         *    });
         *
         *   3.方法引用:   System.out::println 与 void accept(String s)签名一样就可以
         *     childStrList.forEach(System.out::println);
         */
        /*childStrList.forEach((String child) -> {
            System.out.println(child);
        });*/
       // childStrList.forEach(System.out::println);
        //方法引用方式,引用自己的打印
       // childStrList.forEach(CuratorClient::print);

        //判断节点是否存在,存在则返回状态,不存在返回null
       // Stat existStat = curatorClient.client.checkExists().forPath(ROOT_NODE);
        //System.out.println(existStat);
        //判断节点是否存在,如果父节点不存在则创建父节点路径,子节点存在则返回状态,不存在返回null
        //Stat existStat2 = curatorClient.client.checkExists().creatingParentsIfNeeded().forPath(ROOT_NODE + "2" + "/child2");
        //System.out.println("节点存在:" + existStat2);



        //删除节点 节点存在子节点, 删除失败,
        //curatorClient.client.delete().forPath(ROOT_NODE);
        //删除节点, 能删除掉带有子节点的节点
        //curatorClient.client.delete().deletingChildrenIfNeeded().forPath(ROOT_NODE);
        //删除节点 guaranteed() 这个方法确保删除,只要zookeeper接到这个指令,即使客户端与zookeeper断链,zookeeper也会删除节点
        //curatorClient.client.delete().guaranteed().deletingChildrenIfNeeded().forPath(ROOT_NODE);



        //zookeeper节点监听(watcher) 在获取数据的时候对该节点增加监听watcher,但是只能监听一次
        //他监听的节点必须存在
        /*curatorClient.client.getData().usingWatcher((CuratorWatcher)(WatchedEvent event) ->{
            System.out.println("对节点[" + event.getPath() +"] 进行了操作,操作类型:" + event.getType().name());
        }).forPath(ROOT_NODE);*/



        //zookeeper节点监听  程序不退出时的永久监听,true 表示数据压缩,节省空间
        //他监听的节点可以不存在
        //NodeCache noderCache = new NodeCache(curatorClient.client,"/curator15" ,false);
        //默认是不获取数据
        //noderCache.start();
        //true 表示把节点的数据获取出来
        //noderCache.start(true);
        //如果true可以这么获取到数据
        //creteSystem.out.println("nodeCache data:" + new String(noderCache.getCurrentData().getData()));

        /*noderCache.getListenable().addListener(() -> {
            System.out.println("nodeCache 监听事件....");
            System.out.println("节点数据:" + new String(noderCache.getCurrentData().getData()));
            System.out.println("节点:" + noderCache.getPath());
        });*/




        //监听子节点 true表示缓存数据
        PathChildrenCache childrenCache = new PathChildrenCache(curatorClient.client,"/root",true);
        //启动监听
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);
        //监听器PathChildrenCacheListener
        //只能监听一层子节点
        childrenCache.getListenable().addListener(
                (CuratorFramework client, PathChildrenCacheEvent event) -> {
                    System.out.println("子节点监听事件了...");

                    System.out.println("TYPE:" + event.getType().name());
                    System.out.println(new String(event.getData().getData()));
                    event.getInitialData().forEach((ChildData data) -> {
                        System.out.println("初始化数据"+data.getPath());
                    });
        });

        Thread.sleep(1000000);
    }


    public static void print(String string){
        System.out.println("curator:" + string);
    }
}
