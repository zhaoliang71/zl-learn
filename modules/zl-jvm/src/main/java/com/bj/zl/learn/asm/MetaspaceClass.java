package com.bj.zl.learn.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class MetaspaceClass extends ClassLoader {

    public static void classLoading() {
        // 类持有
        List<Class<?>> classes = new ArrayList<Class<?>>();

        // 循环1000w次生成1000w个不同的类
        for (int i = 0; i < 10000000; ++i) {

            ClassWriter cw = new ClassWriter(0);
            // 定义一个类名称为Class{i}，它的访问域为public，父类为java.lang.Object，不实现任何接口
            cw.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "Class" + i, null,
                    "java/lang/Object", null);
            // 定义构造函数<init>方法
            MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>",
                    "()V", null, null);
            // 第一个指令为加载this
            mw.visitVarInsn(Opcodes.ALOAD, 0);
            // 第二个指令为调用父类Object的构造函数
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object",
                    "<init>", "()V", false);
            // 第三条指令为return
            mw.visitInsn(Opcodes.RETURN);
            mw.visitMaxs(1, 1);
            mw.visitEnd();

            MetaspaceClass test = new MetaspaceClass();
            byte[] code = cw.toByteArray();
            // 定义类
            Class<?> loadClass = test.defineClass("Class" + i, code, 0, code.length);

            classes.add(loadClass);
        }
    }
}