/**
 * create by Administrator
 * 2019-12-09
 */
package com.bj.zl.learn.mybatis.executor;

import com.bj.zl.learn.mybatis.domain.Configuration;
import com.bj.zl.learn.mybatis.domain.DataSource;
import com.bj.zl.learn.mybatis.parsing.SQLTokenParser;
import com.bj.zl.learn.mybatis.pool.DataSourcePool;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private Configuration configuration;

    private DataSourcePool dataSourcePool;

    public Executor(Configuration configuration) {
        dataSourcePool = DataSourcePool.getInstance(configuration.getEnvironment());
        this.configuration = configuration;
    }

    /**
     * 查询List
     * @param <T>
     * @param args
     * @return
     */
    public <T> List<T> query(String key, Object args) {
        //
        List<T> resultList = new ArrayList<>();
        //封装返回结果
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection= null;
        try {
            //从数据源池获取连接
            connection = dataSourcePool.getConnection();

            //预编译
            String sql = SQLTokenParser.parse(configuration.getMapperStatementMap().get(key).getSql());
            preparedStatement=connection.prepareStatement(sql);
            //设置参数
            if (args instanceof Integer){
                preparedStatement.setInt(1, (Integer) args);
            }
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            handlerResult(resultSet,resultList,configuration.getMapperStatementMap().get(key).getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (preparedStatement !=null){
                    preparedStatement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
                dataSourcePool.releaseConn(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //封装jdbc代码,返回结果
        return resultList;
    }

    private <T> void handlerResult(ResultSet resultSet, List<T> resultList, String resultType) {

        try{
            Class<T> tClass = (Class<T>) Class.forName(resultType);
            while (resultSet.next()){
                Object entity = tClass.newInstance();
                //获取返回结果的 元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                //获取OOrderInfo 声明的成员变量
                Field[] declaredFields = entity.getClass().getDeclaredFields();
                for(int i=0;i<columnCount;i++){
                    //数据表字段的名称
                    String columnName = metaData.getColumnName(i + 1).replace("_","");
                    for(int j =0;j<declaredFields.length;j++){
                        //获取属性名称
                        String name = declaredFields[j].getName();
                        if (columnName.equalsIgnoreCase(name)){
                            //获取当前属性
                           Field field = entity.getClass().getDeclaredField(name);
                           //设置当前属性可访问
                           field.setAccessible(true);
                           if (field.getType().getSimpleName().equals("Integer")){
                               int value = resultSet.getInt(metaData.getColumnName(i + 1));
                               field.set(entity,value);
                           }else if (field.getType().getSimpleName().equals("String")) {
                               String value = resultSet.getString(metaData.getColumnName(i + 1));
                               field.set(entity, value);
                           }else if (field.getType().getSimpleName().equals("Long")) {
                               long value = resultSet.getLong(metaData.getColumnName(i + 1));
                               field.set(entity, value);
                           }else if (field.getType().getSimpleName().equals("Date")) {
                               Date value = resultSet.getDate(metaData.getColumnName(i + 1));
                               field.set(entity, value);
                           }else if (field.getType().getSimpleName().equals("BigDecimal")) {
                               BigDecimal value = resultSet.getBigDecimal(metaData.getColumnName(i + 1));
                               field.set(entity, value);
                           }

                        }

                    }
                }
                resultList.add((T) entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
