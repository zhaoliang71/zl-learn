package com.bj.zl.learn.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义classloader加载class文件
 *
 */
public class MyJavaClassLoader extends ClassLoader {

    private File classPathFile;

    public MyJavaClassLoader() {
        String classFile = MyJavaClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classFile);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyJavaClassLoader.class.getPackage().getName() + "." +  name;

        if (classPathFile != null) {
           File classFile = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");

           if (classFile.exists()) {

               FileInputStream fis = null;
               ByteArrayOutputStream bos = null;

               try {
                   fis = new FileInputStream(classFile);
                   bos = new ByteArrayOutputStream();

                   byte[] buff = new byte[4096];

                   int len;
                   while ((len = fis.read(buff)) != -1) {
                       bos.write(buff, 0, len);
                   }

                   return defineClass(className, bos.toByteArray(), 0, bos.size());
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   try {
                       fis.close();
                       bos.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
        }
        return null;
    }
}