/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq.head;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 4. Headers Exchange, 基于消息内容中的headers属性进行匹配；（用得比较少）
 *  Headers 类型的Exchanges是不处理路由键的，而是根据发送的消息内容中的headers属性进行匹配。
 * 在绑定Queue与Exchange时指定一组键值对；当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时指定的键值对进行匹配；
 * 如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，键值对的值可以是任何类型。
 */
public class Sender {


    private static final String QUEUE_NAME = "headerQueue";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";
    private static final String exchangeDeclare = "headerExchange";
    private static final String routrKey = "headerRouteKey";

    public static void main(String[] args) {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_HOST);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(5672);//rabbitMQ 默认端口
        connectionFactory.setVirtualHost("/");//虚拟机 默认/
        Connection connection=null;
        Channel channel=null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();

            //声明直连交换机
            //channel.exchangeDeclare(exchangeDeclare, BuiltinExchangeType.HEADERS);

            //声明队列,队列名称 QUEUE_NAME,是不是需要持久化,是不是独享的,是不是自动删除,map别的一些参数
            //channel.queueDeclare(QUEUE_NAME,true,false,false,null);

            //队列和交换机通过路由KEY绑定起来
            //channel.queueBind(QUEUE_NAME,exchangeDeclare,"");
            //设置消息头键值对信息
            Map<String,Object> headers = new HashMap<>();
            //这里的x-match有两种类型
            //all:表示所有的键值对都匹配才能接受消息
            //any:表示只要有任意键值对匹配就能接受到消息
            headers.put("x-match","any");
            headers.put("name","zhangsan");
            //headers.put("age","20");
            String message = "hello world~!";
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.headers(headers);
            for (int i=0;i<10;i++){
                channel.basicPublish(exchangeDeclare,"",builder.build(),(message+i).getBytes("UTF-8"));
                System.out.println("[x] send'"+(message+i)+"'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel != null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
