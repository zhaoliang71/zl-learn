/**
 * create by Administrator
 * 2019-11-25
 */
package com.bj.zl.learn.mybatis.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类,但不是真正的代理类,真正的代理类是JDK自动生成的
 *
 * InvocationHandler 需要实现这个接口
 */
public class PoxyClass implements InvocationHandler {
    //持有一个目标类的引用
    Object target;

    public PoxyClass(Object target) {
        this.target = target;
    }

    /**
     * 对目标方法的增强
     * @param proxy 表示的是JDK自动生成的代理类
     * @param method
     * @param args 参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("增强前............");

        Object object = method.invoke(target,args);
        System.out.println("增强后............");
        return object;
    }
}
