/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {


    private static final String QUEUE_NAME = "hello";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";

    public static void main(String[] args) {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_HOST);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(5672);//rabbitMQ 默认端口
        connectionFactory.setVirtualHost("/");//虚拟机 默认/

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //声明队列,队列名称 QUEUE_NAME,是不是需要持久化,是不是独享的,是不是自动删除,map别的一些参数
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "hello world~!";
            //发送消息(发送到默认交换机AMQP Default交换机)
            //如果有一个队列名称跟Routing key相等,那么消息会路由到这个队列
            //没有指定交换机,也没有指定路由KEY,直接发送到默认交换机的默认路由key上
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
            System.out.println("[x] send'"+message+"'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
