/**
 * create by Administrator
 * 2019-11-27
 */
package com.bj.zl.learn.mybatis.session;

import com.bj.zl.learn.mybatis.domain.Configuration;
import com.bj.zl.learn.mybatis.executor.Executor;

public class SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {

        return new SqlSession(new Executor(configuration));
    }
}
