/**
 * create by Administrator
 * 2019-11-04
 */
package com.bj.zl.learn.mybatis.proxy;

/**
 * 静态代理类
 */
public class ProxyClass implements TargetClass{
    //持有一个目标类的引用
    private TargetClass object;

    public ProxyClass(TargetClass object) {
        this.object = object;
    }

    @Override
    public void say() {
        //对目标类进行增强
        System.out.println("功能增强前");
        object.say();
        System.out.println("功能增强后");
    }
}
