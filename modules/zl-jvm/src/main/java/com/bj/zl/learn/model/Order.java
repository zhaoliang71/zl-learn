package com.bj.zl.learn.model;

import java.math.BigDecimal;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Order {

    //private static final ThreadPoolExecutor THREAD_POOL =  new ThreadPoolExecutor(corePoolSize,corePoolSize * 2,3, TimeUnit.SECONDS);

    private int id;

    private String name;

    private BigDecimal money;

    private byte[] bytes = new byte[1024 * 1024]; //1024kb = 1m

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
