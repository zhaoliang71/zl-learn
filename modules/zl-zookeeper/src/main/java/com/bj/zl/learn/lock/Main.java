/**
 * create by Administrator
 * 2019-09-22
 */
package com.bj.zl.learn.lock;

import com.bj.zl.learn.zookeeper.main.service.lockService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        Main lockService = new Main();
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
}
