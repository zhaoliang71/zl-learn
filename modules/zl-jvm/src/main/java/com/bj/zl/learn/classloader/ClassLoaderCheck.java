package com.bj.zl.learn.classloader;

import java.util.Date;

/**
 * 类加载器测试
 *
 */
public class ClassLoaderCheck {

    public static void main(String[] args) {

        try {
            //TODO null --> bootstrap classLoader ,C++语言实现的，没有名称
            System.out.println(Date.class.getClassLoader());

            //TODO java.lang.NullPointerException，没有父的classloader
            //System.out.println(String.class.getClassLoader().getParent());

            //TODO sun.misc.Launcher$AppClassLoader@18b4aac2
            //System.out.println(Test.class.getClassLoader());

            //TODO sun.misc.Launcher$ExtClassLoader@74a14482
            //System.out.println(Test.class.getClassLoader().getParent());


            //System.out.println(com.bjpowernode.jvm.String.class.getClassLoader());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}