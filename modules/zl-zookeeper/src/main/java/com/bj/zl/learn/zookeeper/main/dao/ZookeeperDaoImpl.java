/**
 * create by Administrator
 * 2019-09-22
 */
package com.bj.zl.learn.zookeeper.main.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperDaoImpl implements ZookeeperDao {
    @Autowired
    private SqlSession submitSqlSession;
    private static final String S_N = "com.bj.zl.learn.zookeeper.main.dao.ZookeeperDao";
    @Override
    public int check() {
        return submitSqlSession.selectOne(S_N +".check");
    }

    @Override
    public void des(int i) {
        submitSqlSession.update(S_N+".des",i);
    }
}
