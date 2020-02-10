/**
 * create by Administrator
 * 2020-02-05
 */
package com.bj.zl.learn.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = "com.bj.zl.learn.spring")
@EnableAspectJAutoProxy //开启aop功能
public class Config {
}
