package com.bj.zl.learn.spring;

import com.bj.zl.learn.spring.config.Config;
import com.bj.zl.learn.spring.service.impl.SmsServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {

		//注解版本的方法
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		//SmsService smsService = context.getBean("smsServiceImpl", SmsService.class);

		SmsServiceImpl smsService = context.getBean("smsServiceImpl", SmsServiceImpl.class);

		smsService.sendSms();
	}
}
