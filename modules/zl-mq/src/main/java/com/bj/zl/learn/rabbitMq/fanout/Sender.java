/**
 * create by Administrator
 * 2020-01-07
 */
package com.bj.zl.learn.rabbitMq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 3. Fanout Exchange，（扇形，广播交换机）匹配所有绑定的队列，不需要规则；（不需要匹配，也就是不需要路由key）
 * 可以发给多个端
 */
public class Sender {


    private static final String QUEUE_NAME = "fanoutQueue";
    private static final String IP_HOST = "192.168.109.133";
    private static final String username = "guest";
    private static final String password = "guest";
    private static final String exchangeDeclare = "fanoutExchange";
    private static final String routrKey = "fanoutRouteKey.#";

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
            channel.exchangeDeclare(exchangeDeclare, BuiltinExchangeType.FANOUT);

            //声明队列,队列名称 QUEUE_NAME,是不是需要持久化,是不是独享的,是不是自动删除,map别的一些参数
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);

            //队列和交换机通过路由KEY绑定起来
            channel.queueBind(QUEUE_NAME,exchangeDeclare,"");

            String message = "hello world~!";

            for (int i=0;i<10;i++){
                channel.basicPublish(exchangeDeclare,"",null,(message+i).getBytes("UTF-8"));
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
