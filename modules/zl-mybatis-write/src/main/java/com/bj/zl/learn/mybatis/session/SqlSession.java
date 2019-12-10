/**
 * create by Administrator
 * 2019-12-09
 */
package com.bj.zl.learn.mybatis.session;

import com.bj.zl.learn.mybatis.executor.Executor;
import com.bj.zl.learn.mybatis.poxy.MapperInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.List;

public class SqlSession {

    private Executor executor;

    public SqlSession(Executor executor) {
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{tClass},new MapperInvocationHandler(this));
    }

    public <T> T selectOne(String key, Object args) {

        List<T> list = this.executor.query(key,args);

        if (list.size() == 1){
            return list.get(0);
        }else {
            throw new RuntimeException();
        }
    }
}
