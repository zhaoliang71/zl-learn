/**
 * create by Administrator
 * 2019-11-25
 */
package com.bj.zl.learn.mybatis.proxy.dynamic;

import java.lang.reflect.Proxy;

public class Client {

    public static void main(String[] args) {

        String name = "sun.misc.ProxyGenerator.saveGeneratedFiles";
        System.setProperty(name,"true");


        TargetClass targetClass = new TargetClassImpl();
        //代理类
        TargetClass poxyTarget = (TargetClass)Proxy.newProxyInstance(Client.class.getClassLoader(),
                new Class<?>[]{TargetClass.class},
                new PoxyClass(targetClass));

        poxyTarget.say();
    }
}
