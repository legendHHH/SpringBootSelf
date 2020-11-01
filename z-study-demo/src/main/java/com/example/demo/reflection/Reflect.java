package com.example.demo.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取类对象的四种方式
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/1
 */
public class Reflect {

    private static final Logger log = LoggerFactory.getLogger(Reflect.class);

    private int q = 2 << 2;

    static {
        int p = 2 << 1;
        System.out.println(p);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Map<String, Class> classMap = getClassObject();
        System.out.println(classMap.toString());

        Class userClass = classMap.get("userClass");
        int modifiers = userClass.getModifiers();
        String name = userClass.getName();
        String simpleName = userClass.getSimpleName();
        Annotation[] annotations = userClass.getAnnotations();
        Field[] fields = userClass.getFields();
        log.info("modifiers:{},name:{},simpleName:{},annotations:{},fields:{}",
                modifiers, name,simpleName,annotations,fields);
    }


    public static Map<String, Class> getClassObject() throws ClassNotFoundException {

        Map<String, Class> classMap = new HashMap<>(16);
        Class<Users> userClass = Users.class;
        Class<?> userClassForName = Class.forName("com.example.demo.reflection.Users");
        Class<? extends Users> getClass = new Users().getClass();
        Class<?> loadClass = Users.class.getClassLoader().loadClass("com.example.demo.reflection.Users");

        log.info("userClass:{}", userClass);
        log.info("userClassForName:{}", userClassForName);
        log.info("getClass:{}", getClass);
        log.info("loadClass:{}", loadClass);


        classMap.put("userClass", userClass);
        classMap.put("userClassForName", userClassForName);
        classMap.put("getClass", getClass);
        classMap.put("loadClass", loadClass);
        return classMap;
    }
}
