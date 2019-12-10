/**
 * create by Administrator
 * 2019-12-03
 */
package com.bj.zl.learn.mybatis.domain;

import java.util.List;
import java.util.Map;

/**
 * mybatis-config封装对象
 */
public class MyEnvironment {

    private Map<String,DataSource> dataSources;

    public MyEnvironment(Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public Map<String, DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
    }
}
