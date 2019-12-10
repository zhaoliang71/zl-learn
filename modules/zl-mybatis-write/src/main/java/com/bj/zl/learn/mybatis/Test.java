/**
 * create by Administrator
 * 2019-11-25
 */
package com.bj.zl.learn.mybatis;

import com.bj.zl.learn.mybatis.domain.OOrderInfo;
import com.bj.zl.learn.mybatis.io.MyResource;
import com.bj.zl.learn.mybatis.mapper.OOrderInfoMapper;
import com.bj.zl.learn.mybatis.session.SqlSession;
import com.bj.zl.learn.mybatis.session.SqlSessionFactory;
import com.bj.zl.learn.mybatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class Test {

    public static void main(String[] args) {
        //
        InputStream inputStream = MyResource.getResourceAsStream("mybatis-config.xml");
        //构建SqlSessionFactory  解析配置文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //打开数据库 连接 ,获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        //获取maaper对象
        OOrderInfoMapper oOrderInfoMapper = sqlSession.getMapper(OOrderInfoMapper.class);

        OOrderInfo oOrderInfo = oOrderInfoMapper.selectByPrimaryKey(1);

        //执行数据库操作
        int i = 0;
    }
}
