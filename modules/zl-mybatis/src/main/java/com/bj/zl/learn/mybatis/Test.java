package com.bj.zl.learn.mybatis;

import com.bj.zl.learn.mybatis.mapper.UUserInfoMapper;
import com.bj.zl.learn.mybatis.model.UUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试mybatis框架
 *
 */
@Slf4j //日志门面
public class Test {

    public static void main(String[] args) {

        SqlSession session = null;
        try {
            //第一步：读取mybatis-config.xml配置文件
            //等同于InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

            //第二步：构建SqlSessionFactory
            //构建document对象,xpath对象, 用他们解析XML,并封装成 Configuration对象,最后返回一个DeafaultSqlSessionFactory
            //Xpath基于表达式查询方式解析 XML
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            //第三步：打开SqlSession
            //创建执行器,封装sqlSession
            session = sqlSessionFactory.openSession();

            //第四步：从获取 Configuration对象 Mapper接口对象
            UUserInfoMapper uUserInfoMapper = session.getMapper(UUserInfoMapper.class);

            //第五步：调用Mapper接口对象的方法操作数据库；
            UUserInfo uUserInfo = uUserInfoMapper.selectByPrimaryKey(1);

            //第六步：业务处理
            log.info("查询结果: " + uUserInfo.getId() + "--" + uUserInfo.getPhone());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }
    }
}