/**
 * create by Administrator
 * 2019-11-27
 */
package com.bj.zl.learn.mybatis.xml;

import com.bj.zl.learn.mybatis.domain.Configuration;
import com.bj.zl.learn.mybatis.domain.DataSource;
import com.bj.zl.learn.mybatis.domain.MyEnvironment;
import com.bj.zl.learn.mybatis.domain.MyMapperStatement;
import com.bj.zl.learn.mybatis.io.MyResource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlConfigBuilder {


    private MyXPathParse xPathParse;

    public XmlConfigBuilder(InputStream inputStream) {
        xPathParse = new MyXPathParse(inputStream);
    }

    public Configuration parse() {
        // node 代表</configuration/environment/dataSource>这个标签下的所有数据
        Node environmentNode = (Node)xPathParse.evaluate("/configuration/environment");

        NodeList envNodes = environmentNode.getChildNodes();
        Map<String,DataSource> dataSources = new HashMap<String, DataSource>();
        for(int i =0 ;i < envNodes.getLength(); i++){
            Node item = envNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE){
                //元素节点,说明不是空白
                String id = item.getAttributes().getNamedItem("id").getNodeValue();
                NodeList dataSourceProperiesNodeList = item.getChildNodes();

                Map<String,String> environmentMap =  new HashMap<String, String>();
                for(int j =0 ;j < dataSourceProperiesNodeList.getLength(); j++){
                    Node dataSource = dataSourceProperiesNodeList.item(j);
                    if (dataSource.getNodeType() == Node.ELEMENT_NODE){
                        //元素节点,说明不是空白
                        String name = dataSource.getAttributes().getNamedItem("name").getNodeValue();
                        String value = dataSource.getAttributes().getNamedItem("value").getNodeValue();
                        environmentMap.put(name,value);
                    }
                }
                dataSources.put(id,new DataSource(environmentMap.get("driver"),
                        environmentMap.get("url"),environmentMap.get("username"),environmentMap.get("password")));
            }
        }
        // node 代表</configuration/mappers>这个标签下的所有数据
        Map<String,MyMapperStatement> myMapperStatementMap =  new HashMap<String, MyMapperStatement>();
        Node mapperNode = (Node)xPathParse.evaluate("/configuration/mappers");
        NodeList mapperNodeList = mapperNode.getChildNodes();
        for (int i=0;i < mapperNodeList.getLength();i++){
            Node item = mapperNodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE){
                String resource = item.getAttributes().getNamedItem("resource").getNodeValue();
                InputStream inputStream = MyResource.getResourceAsStream(resource);
                this.xPathParse = new MyXPathParse(inputStream);
                Node mapperXmlNode = (Node) xPathParse.evaluate("/mapper");
                String namespace = mapperXmlNode.getAttributes().getNamedItem("namespace").getNodeValue();
                NodeList mapperXmlNodes = mapperXmlNode.getChildNodes();
                for(int j =0 ;j < mapperXmlNodes.getLength(); j++){
                    Node sql = mapperXmlNodes.item(j);
                    if (sql.getNodeType() == Node.ELEMENT_NODE){
                        //元素节点,说明不是空白
                        String id = sql.getAttributes().getNamedItem("id").getNodeValue();
                        String parameterType = sql.getAttributes().getNamedItem("parameterType").getNodeValue();
                        String resultType = sql.getAttributes().getNamedItem("resultType").getNodeValue();
                        String sqlStr = sql.getTextContent();
                        MyMapperStatement myMapperStatement = new MyMapperStatement(namespace,id,parameterType,resultType,sqlStr);
                        myMapperStatementMap.put(namespace + "." + id,myMapperStatement);
                    }
                }

            }
        }

        return new Configuration(new MyEnvironment(dataSources),myMapperStatementMap);
    }
}
