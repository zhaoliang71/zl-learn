/**
 * create by Administrator
 * 2019-12-31
 */
package com.bj.zl.learn.redis.spring.domain;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 5433623261047727959L;
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
