/**
 * create by Administrator
 * 2020-01-26
 */
package com.bj.zl.learn.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.ServiceLoader;

public class Test {

    /*public static void main(String[] args) {

        ServiceLoader<Video> serviceLoader = ServiceLoader.load(Video.class);
        serviceLoader.forEach((obj)->{
            System.out.println(obj);
        });
    }*/

    public static void main(String[] args) {

        ExtensionLoader<Video> serviceLoader = ExtensionLoader.getExtensionLoader(Video.class);
        serviceLoader.getExtension("spring");
    }
}
