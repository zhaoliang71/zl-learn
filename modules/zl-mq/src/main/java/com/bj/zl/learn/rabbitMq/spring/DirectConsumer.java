/**
 * create by Administrator
 * 2020-01-12
 */
package com.bj.zl.learn.rabbitMq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class DirectConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("DirectConsumer 接收到消息-->" + message);
    }
}
