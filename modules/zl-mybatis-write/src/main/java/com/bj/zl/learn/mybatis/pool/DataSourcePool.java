/**
 * create by Administrator
 * 2019-12-09
 */
package com.bj.zl.learn.mybatis.pool;

import com.bj.zl.learn.mybatis.domain.DataSource;
import com.bj.zl.learn.mybatis.domain.MyEnvironment;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSourcePool implements DataSourceInterface {

    private MyEnvironment environment;

    private Connection connection;

    private static DataSourcePool instance;

    private static  final int  POOL_SIZE = 30;

    private List<Connection> pool ;

    public DataSourcePool(MyEnvironment environment) {
        this.environment = environment;
        pool = new ArrayList<>(POOL_SIZE);
        this.createConnection();
    }

    public static DataSourcePool getInstance(MyEnvironment environment){
        if (instance == null){
            instance = new DataSourcePool(environment);
        }
        return instance;
    }


    /**
     * 创建连接
     */
    public void createConnection(){
        try{
            DataSource dataSource = environment.getDataSources().get("dev");
            Class driverClass = Class.forName(dataSource.getDriver());
            for (int i = 0; i< POOL_SIZE ; i++){
                connection = DriverManager.getConnection(dataSource.getUrl(),dataSource.getUsername(),dataSource.getPassword());
                pool.add(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized Connection getConnection(String username, String password) throws SQLException {
        if (pool.size() > 0){
            Connection connection = pool.get(0);
            pool.remove(connection);
            return connection;
        }
        return null;
    }

    /**
     * 释放连接
     * @param connection
     */
    public synchronized void releaseConn(Connection connection){
        pool.add(connection);
    }

}
