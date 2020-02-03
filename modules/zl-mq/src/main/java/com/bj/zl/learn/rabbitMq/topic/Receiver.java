/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private static final String QUEUE_NAME = "topicQueue";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";
    private static final String exchangeDeclare = "topicExchange";
    private static final String routrKey = "topicRouteKey";
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

            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            System.out.println("[*] waiting for message. to exit press ctrl + c");

            DeliverCallback deliverCallback = (consumerTag,message) ->{
                String messageText = new String(message.getBody(),"UTF-8");
                System.out.println("[x] recevied message '"+ messageText +"'");
            };
            //消费   队列名, 确认是否消费,当有消息的时候回调函数,取消回调
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            /*if (channel != null){
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
            }*/
        }
    }
}
