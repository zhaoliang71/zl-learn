/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 根据路由键通配符匹配进行路由消息队列；
 * 符号#匹配零个或多个单词，符号*匹配一个单词，用.隔开每一个单词，举例说明：
 * beijing.* 匹配 beijing.queue（模糊匹配）
 * beijing.# 匹配 beijing.queue.abc
 */
public class Sender {


    private static final String QUEUE_NAME = "topicQueue";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";
    private static final String exchangeDeclare = "topicExchange";
    private static final String routrKey = "topicRouteKey.#";

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
            channel.exchangeDeclare(exchangeDeclare, BuiltinExchangeType.TOPIC);

            //声明队列,队列名称 QUEUE_NAME,是不是需要持久化,是不是独享的,是不是自动删除,map别的一些参数
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);

            //队列和交换机通过路由KEY绑定起来
            channel.queueBind(QUEUE_NAME,exchangeDeclare,routrKey);

            String message = "hello world~!";

            for (int i=0;i<10;i++){
                channel.basicPublish(exchangeDeclare,"topicRouteKey.s1",null,(message+i).getBytes("UTF-8"));
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
