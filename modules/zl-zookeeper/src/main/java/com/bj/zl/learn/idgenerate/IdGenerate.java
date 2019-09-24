/**
 * create by Administrator
 * 2019-09-18
 */
package com.bj.zl.learn.idgenerate;

import com.bj.zl.learn.curator.CuratorClient;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper
 * 通过有序节点获取全局唯一的ID
 */
public class IdGenerate {

    private static final String ID_NODE = "/NODE/zl";
    public static void main(String[] args) throws Exception {
        IdGenerate idGenerate = new IdGenerate();
        idGenerate.runThread();
    }

    /**
     * 通过zookeeper有序节点来生成唯一ID
     * @return
     * @throws Exception
     */
    private String idGen() throws Exception {
        CuratorClient curatorClient = new CuratorClient();

        if (null == curatorClient.getClient().checkExists().forPath(ID_NODE)){
            String s = curatorClient.getClient().create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(ID_NODE);

            //删除创建的节点,节省zookeeper空间
            curatorClient.getClient().delete().forPath(s);
            return s.substring(ID_NODE.length());
        }
        return "";
    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private void runThread() throws InterruptedException {

        long awaitTime = 5*1000;

        ExecutorService service = Executors.newFixedThreadPool(16);

        for(int i=0 ;i<16; i++){

            Thread.sleep(50);

            service.submit(()->{
                try {
                    //等到所有线程均提交任务后,执行业务代码
                    countDownLatch.await();
                    System.out.println(idGen());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        countDownLatch.countDown();

        try{
            service.shutdown();
            if (!service.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
                service.shutdownNow();
            };
        }catch (InterruptedException e){
            service.shutdownNow();
        }
    }
}
