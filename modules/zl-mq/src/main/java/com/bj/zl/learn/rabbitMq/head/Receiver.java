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

public class Receiver {

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
            //设置消息头键值对信息
            Map<String,Object> headers = new HashMap<>();
            //这里的x-match有两种类型
            //all:表示所有的键值对都匹配才能接受消息
            //any:表示只要有任意键值对匹配就能接受到消息
            headers.put("x-match","any");
            headers.put("name","zhangsan");
            headers.put("age","20");
            //声明直连交换机
            channel.exchangeDeclare(exchangeDeclare, BuiltinExchangeType.HEADERS);
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            //队列和交换机通过路由KEY绑定起来
            channel.queueBind(QUEUE_NAME,exchangeDeclare,"",headers);
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
