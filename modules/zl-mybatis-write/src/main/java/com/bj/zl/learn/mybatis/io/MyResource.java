/**
 * create by Administrator
 * 2019-11-25
 */
package com.bj.zl.learn.mybatis.io;

import java.io.InputStream;

public class MyResource {


    public static InputStream getResourceAsStream(String resource){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
