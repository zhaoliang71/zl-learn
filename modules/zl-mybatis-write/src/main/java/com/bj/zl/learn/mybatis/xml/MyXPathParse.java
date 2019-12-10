/**
 * create by Administrator
 * 2019-11-27
 */
package com.bj.zl.learn.mybatis.xml;

import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class MyXPathParse {

    private Document document;

    private boolean validation = true;

    private EntityResolver entityResolver;

    private XPath xPath;


    public MyXPathParse(InputStream inputStream) {
        xPath = initXPath();
        entityResolver = new XMLMapperEntityResolver();
        document = createDocument(new InputSource(inputStream));
    }
    //创建Xpath对象
    public XPath initXPath(){
        XPathFactory xPathFactory = XPathFactory.newInstance();
        return xPathFactory.newXPath();
    }
    //创建Document
    public Document createDocument(InputSource inputSource){


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //是否验证-生成解析器在解析时验证XML内容
        factory.setValidating(validation);
        //设置是否支持命名空间(对 XML 名称空间的支持)
        factory.setNamespaceAware(false);
        //是否忽略注释
        factory.setIgnoringComments(true);
        //是否忽略元素空白
        factory.setIgnoringElementContentWhitespace(false);
        //是否将CDATA节点转换为文本节点(术语 CDATA 指的是不应由 XML 解析器进行解析的文本数据)
        factory.setCoalescing(false);
        //是否展开实体引用节点,这里是指SQL片段引用
        factory.setExpandEntityReferences(true);

        try {
            //创建一个DocumentBuilder对象
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            //设置解析mybatis XML文档节点的解析器
            documentBuilder.setEntityResolver(entityResolver);
            //异常处理
            documentBuilder.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException exception) throws SAXException {

                }

                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });
            return documentBuilder.parse(inputSource);
        } catch (Exception e) {
            throw new RuntimeException("Document 解析错误"+e,e);
        }
    }

    /**
     * XPath 解析document文件
     * @param expression
     * @return
     */
    public Object evaluate(String expression){

        try {
            return xPath.evaluate(expression,document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
           throw new RuntimeException(e);
        }
    }

    public XPath getxPath() {
        return xPath;
    }

    public void setxPath(XPath xPath) {
        this.xPath = xPath;
    }
}
