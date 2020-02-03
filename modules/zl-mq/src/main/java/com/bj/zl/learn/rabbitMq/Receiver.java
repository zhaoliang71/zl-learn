/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private static final String QUEUE_NAME = "hello";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_HOST);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
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
        }

    }
}
