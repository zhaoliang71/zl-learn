package com.bj.zl.learn.spring.service.impl;

import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl {//implements SmsService {

	public void sendSms() {
		System.out.println("sendSms: spring aop......");
	}
}