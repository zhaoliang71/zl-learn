/**
 * create by Administrator
 * 2020-01-12
 */
package com.bj.zl.learn.rabbitMq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {
    @Autowired
    @Qualifier("directTemplate")
    private AmqpTemplate direct;

    /**
     * 演示三种交换机的使用
     * @param message
     */
    public void sendMessage(Object message){
        System.out.println("send message :"+message);
        //交换机指定为直连交换机,指定路由key为directKey
        direct.convertAndSend("directKey","[Direct发送的消息]"+message);
    }
}
