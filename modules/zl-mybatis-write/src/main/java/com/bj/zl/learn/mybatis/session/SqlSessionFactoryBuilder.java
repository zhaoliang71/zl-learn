/**
 * create by Administrator
 * 2019-11-27
 */
package com.bj.zl.learn.mybatis.session;

import com.bj.zl.learn.mybatis.domain.Configuration;
import com.bj.zl.learn.mybatis.xml.XmlConfigBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {


    public SqlSessionFactory build(InputStream inputStream) {
        //初始化的过程,通过inputStream 获取document,并初始化XPath
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(inputStream);
        Configuration configuration = xmlConfigBuilder.parse();
        return new SqlSessionFactory(configuration);
    }
}
