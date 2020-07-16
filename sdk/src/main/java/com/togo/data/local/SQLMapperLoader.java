package com.togo.data.local;

import org.objectweb.asm.*;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * @Author taiyn
 * @Description load mappers which can launch sql
 * @Date 3:49 下午 2020/5/25
 **/
public class SQLMapperLoader extends ClassLoader {

    private static String basePackage = SQLCraftFactory.INSTANCE.createSQLContext().getBasePackage();
    public static byte[] dump(String ClassName, List<String> methodNameList) {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        String replace = basePackage.replace(".", "/");
        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, replace + "/" + ClassName,
                null, "java/lang/Object", null);

        cw.visitSource(ClassName + ".java", null);

        for (String methodName : methodNameList) {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, methodName, "(Ljava/util/Map;)Ljava/util/List;",
                    "(Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)Ljava/util/List<Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;>;", null);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }

    public Class<?> defineClass(String name, byte[] b) {
        // ClassLoader是个抽象类，而ClassLoader.defineClass 方法是protected的
        // 所以我们需要定义一个子类将这个方法暴露出来
        return super.defineClass(name, b, 0, b.length);
    }
}
