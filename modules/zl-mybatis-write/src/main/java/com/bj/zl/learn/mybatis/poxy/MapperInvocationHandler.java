/**
 * create by Administrator
 * 2019-12-09
 */
package com.bj.zl.learn.mybatis.poxy;

import com.bj.zl.learn.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperInvocationHandler implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperInvocationHandler(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //方法声明的类 com.bj.zl.learn.mybatis.mapper.OOrderInfoMapper
        String statementKey = method.getDeclaringClass().getTypeName() + "." +method.getName();


        Object param = args ==null ? null :args[0];
        return sqlSession.selectOne(statementKey,param);
    }
}
