package com.bj.zl.learn.flow;


import com.alibaba.csp.sentinel.context.ContextUtil;
import com.bj.zl.learn.flow.rule.FlowRuleUtil;
import com.bj.zl.learn.flow.service.SentinelResourceServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {


    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            //String logStr = sentinelResourceService.testAnnotation(i++);
            String logStr = sentinelResourceService.testAnnotation2(i++);
            //String logStr = sentinelResourceService.castExceptionMethod(i++);
            //String logStr = sentinelResourceService.booleanMethod(i++);
            //String logStr = sentinelResourceService.testAnnotationHandler(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }

    }

    /**
     * 注解方式  fallback FallbackUtils中降级
     * 用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理
     * @return
     */
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule1();

        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            String logStr = sentinelResourceService.annotationTestFall(i++);
            System.out.println(logStr);
            if(i>100){
                return;
            }
        }
    }
    /**
     * limitApp
     *
     * 测试失败
     * @return
     */
    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        FlowRuleUtil.initRule2();
        ContextUtil.enter("testAnnotationHandlerLimitApp", "caller1");//讲资源与限制的来源绑定
        SentinelResourceServiceImpl sentinelResourceService = (SentinelResourceServiceImpl) context.getBean("sentinelResourceService");
        int i = 0;
        while (true){
            i++;
            String logStr = "";
            if (i%2==0){
                logStr=sentinelResourceService.testAnnotationHandlerLimitApp("defaultsss",i);
            }else {
                logStr= sentinelResourceService.testAnnotationHandlerLimitApp("caller1",i);
            }
            System.out.println(logStr);
            if(i>100){
                return;
            }

        }

    }

}
