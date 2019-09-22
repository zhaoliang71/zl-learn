/**
 * create by Administrator
 * 2019-09-18
 */
package com.bj.zl.learn.idgenerate;

import com.bj.zl.learn.curator.CuratorClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper
 * 方案二：通过节点版本号；生成全局唯一ID
 */
public class IdGenerate02 {

    private static final String ID_NODE = "/NODE/zl";
    public static void main(String[] args) throws Exception {
        IdGenerate02 idGenerate = new IdGenerate02();
        idGenerate.runThread();
        //System.out.println(idGenerate.idGen());
    }

    /**
     * 通过节点版本号；生成全局唯一ID
     * @return
     * @throws Exception
     */
    private long idGen() throws Exception {
        CuratorClient curatorClient = new CuratorClient();
        if (curatorClient.getClient().checkExists().forPath(ID_NODE) == null){
            curatorClient.getClient().create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(ID_NODE);
        }
        Stat stat = curatorClient.getClient().setData().withVersion(-1).forPath(ID_NODE);
        return stat.getVersion();
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
