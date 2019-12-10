/**
 * create by Administrator
 * 2019-12-03
 */
package com.bj.zl.learn.mybatis.domain;
/**
 * mapper  配置封装对象
 */
public class MyMapperStatement {

    private String namespace;

    private String id;

    private String paramType;

    private String resultType;

    private String sql;

    public MyMapperStatement(String namespace, String id, String paramType, String resultType, String sql) {
        this.namespace = namespace;
        this.id = id;
        this.paramType = paramType;
        this.resultType = resultType;
        this.sql = sql;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MyMapperStatement{" +
                "namespace='" + namespace + '\'' +
                ", id='" + id + '\'' +
                ", paramType='" + paramType + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
