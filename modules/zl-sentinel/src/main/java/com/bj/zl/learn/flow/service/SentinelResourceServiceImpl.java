package com.bj.zl.learn.flow.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bj.zl.learn.flow.fallback.FallbackUtils;
import com.bj.zl.learn.flow.handler.HandlerFallUtils;


public class SentinelResourceServiceImpl {


    /**
     * 抛出异常式定义资源
     */
    public String castExceptionMethod(int i) throws InterruptedException {

        try(Entry entry = SphU.entry("castExceptionMethod")){

            //业务
            Thread.sleep(100);
            return "castExceptionMethod:" + i;
        } catch (BlockException e) {
            //限流降级方法
            Thread.sleep(1000);
            return "castExceptionMethod--fall:" + i;
        }
    }

    /**
     * 返回布尔值定义资源
     * @param i
     * @return
     */
    public String booleanMethod(int i){

        if (SphO.entry("booleanMethod")){
            try {
                Thread.sleep(100);
                return "booleanMethod:"+i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                SphO.exit();
            }
        }else {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "booleanMethod--fall:"+i;
        }
        return "";
    }


    /**
     * 注解方式  handler 同类内降级
     * blockHandler 主要作用于限流
     * 若同时配置了 blockHandler 和 fallback，限流时抛出BlockException时，只会进入blockHandler
     * @param i
     * @return
     */
    @SentinelResource(value = "testAnnotation" , blockHandler = "annotationHandler")
    public String testAnnotation(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "testAnnotation:" + i;
    }

    /**
     * 注解方式  handler 同类内降级
     * blockHandler 主要作用于限流
     * 若同时配置了 blockHandler 和 fallback，限流时抛出BlockException时，只会进入blockHandler
     * @param i
     * @return
     */
    @SentinelResource(value = "testAnnotation2" , blockHandler = "annotationHandler", fallback = "annotationFallback")
    public String testAnnotation2(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "testAnnotation:" + i;
    }

    /**
     * 需要public，参数需要多一个BlockException
     * @param i
     * @param d
     * @return
     */
    public String annotationHandler(int i,BlockException d){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "annotationHandler--fall:" + i;
    }

    public String annotationFallback(int i){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "annotationFallback--fall:" + i;
    }


    /**
     * 注解方式  handler HandlerFallUtils中降级
     * blockHandler 主要作用于限流
     * 若同时配置了 blockHandler 和 fallback，限流时抛出BlockException时，只会进入blockHandler
     * @param i
     * @return
     */
    @SentinelResource(value = "testAnnotationHandler" , blockHandler = "testAnnotationHandler",blockHandlerClass = {HandlerFallUtils.class})
    public String testAnnotationHandler(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "testAnnotationHandler:" + i;
    }


    /**
     * 注解方式  fallback FallbackUtils中降级
     * 用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理
     * @param i
     * @return
     */
    @SentinelResource(value = "annotationTestFall" ,fallback = "annotationTestFall",fallbackClass = {FallbackUtils.class})
    public String annotationTestFall(int i){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "annotationTestFall:" + i;
    }



    /**
     * limitApp
     * @param i
     * @return
     */
    @SentinelResource(value = "testAnnotationHandlerLimitApp" , blockHandler = "testAnnotationHandlerLimitApp",blockHandlerClass = {HandlerFallUtils.class})
    public String testAnnotationHandlerLimitApp(String appName,int i){

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "i:" + i+":app:"+appName;
    }
}
