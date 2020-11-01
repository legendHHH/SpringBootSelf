package com.example.demo.reflection;

import com.example.demo.optional.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private static final int q2 = 2 << 2;

    static {
        int p = 2 << 1;
        System.out.println(p);
    }

    public Integer method() {
        log.info("非静态方法可以访问静态变量:{}", q2);
        try {
            Map<String, Class> classObject = getClassObject();
            log.info("非静态方法可以访问静态方法:{}", classObject.toString());
            log.error("静态方法不可以直接访问非静态方法,但是可以通过 new 对象来进行访问");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return q2;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        Map<String, Class> classMap = getClassObject();
        System.out.println(classMap.toString());

        Class userClass = classMap.get("userClass");
        Map<String, Object> classFields = getClassFields(userClass);
        System.out.println(classFields.toString());


        Users oldUser = new Users();
        oldUser.setName("hhhh");
        oldUser.setGender("男");
        oldUser.setSchool(new School("广州大学", "广东省广州市中山路"));
        try {
            operatorDeclearField(userClass, oldUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getClassMethod(userClass);
        getClassConstructors(userClass);


        log.warn("反射的优点：{1.增加程序的灵活性，避免将固有的逻辑程序写死到代码里;2.代码简洁,可读性强，可提高代码的复用率}");
        log.error("反射的缺点：{1.相较直接调用在量大的情景下反射性能下降;2.内部暴露和安全隐患}");


        try {
            testCreateObjectUseTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testCreateObjectUseTime() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            Users user = new Users();
        }
        long end = System.currentTimeMillis();
        log.info("用new的方式创建总耗时：{}", (end - start));

        Class<Users> clazz = Users.class;
        for (int i = 0; i < 10000000; i++) {
            clazz.newInstance();
        }
        long end2 = System.currentTimeMillis();
        log.info("用反射的方式创建总耗时：{}", (end2 - end));

    }

    /**
     * getMethods 方法是获取获取当前类以及父类中所有公有的方法，而 getDeclaredMethods 是获取本类中所有的方法，包括私有的。
     *
     * @param clazz1
     */
    private static void getClassMethod(Class clazz1) {
        //获取当前类以及父类中所有公有的方法
        Method[] methods = clazz1.getMethods();
        for (Method m : methods) {
            System.out.println(m.getModifiers() + " " + m.getName());
        }
        System.out.println("--------------------------------------------");
        // 获取本类中的所有的方法，包括私有的
        Method[] declaredMethods = clazz1.getDeclaredMethods();
        for (Method m : declaredMethods) {
            log.info(m.getModifiers() + " " + m.getName());
        }
    }

    /**
     * getConstructors 获取的是所有公有的构造器，getDeclaredConstructors 获取的是类中所有的构造器，包括私有的。
     *
     * @param userClass
     */
    private static void getClassConstructors(Class userClass) {
        // 获取所有的公有的构造器
        Constructor[] constructors = userClass.getConstructors();
        for (Constructor c : constructors) {
            log.info(c.getModifiers() + " " + c.getName());
        }
        System.out.println("--------------------------------------------");

        // 获取所有的构造器
        Constructor<?>[] declaredConstructors = userClass.getDeclaredConstructors();
        for (Constructor c : declaredConstructors) {
            log.info(c.getModifiers() + " " + c.getName());
        }
    }

    /**
     * 构造器的应用场景就是创建对象，接下来顺便说下创建对象的几种方式
     *
     * @param clazz
     * @throws Exception
     */
    private static void createObjectFromClassConstructors(Class clazz) throws Exception {
        //1、new一个
        Users user1 = new Users();
        //2、类对象的newInstance方法
        Users user2 = (Users) clazz.newInstance();
        //3、构造器的newInstance方法
        Constructor<Users> declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class);
        declaredConstructor.setAccessible(true);
        Users user3 = declaredConstructor.newInstance("jack xu", "男");
        //4、clone
        //5、Serializable

    }

    /**
     * getFields 方法获得的是当前类以及父类中所有公有的字段，而 getDeclaredFields 方法获取的只是本类中的所有字段，包括私有的。
     * 看下另一个方法 getDeclaredField，这是获取当前类的某个具体字段，如下我们获取的是 name 字段，由于 name 字段是私有的，所以需要把访问权限设为 true，最后把 user 对象的 name 字段的值更改为 jack xu。
     *
     * @param clazz
     * @param user
     * @return
     * @throws Exception
     */
    private static Users operatorDeclearField(Class clazz, Users user) throws Exception {
        log.info("原始的对象信息：{}", user);
        Field nameFiled = clazz.getDeclaredField("name");
        nameFiled.setAccessible(true);
        nameFiled.set(user, "jack xu");

        Field genderFiled = clazz.getDeclaredField("gender");
        genderFiled.setAccessible(true);
        genderFiled.set(user, "女");
        log.info("变更后的对象信息：{}", user);
        return user;
    }

    private static Map<String, Object> getClassFields(Class userClass) {
        Map<String, Object> objectMap = new HashMap<>(16);
        int modifiers = userClass.getModifiers();
        String name = userClass.getName();
        String simpleName = userClass.getSimpleName();
        Annotation[] annotations = userClass.getAnnotations();

        //获取当前类以及父类中公有的字段
        Field[] fields = userClass.getFields();
        for (Field f : fields) {
            System.out.println("field:" + f.getModifiers() + " " + f.getName());
        }

        //只能获取当前类中所有的字段
        Field[] declaredFields = userClass.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println("declaredFields:" + f.getModifiers() + " " + f.getName());
        }

        log.info("modifiers:{},name:{},simpleName:{},annotations:{},fields:{}",
                modifiers, name, simpleName, annotations, fields);

        objectMap.put("modifiers", modifiers);
        objectMap.put("className", name);
        objectMap.put("simpleName", simpleName);
        objectMap.put("annotations", annotations);
        objectMap.put("fields", fields);
        objectMap.put("declaredFields", declaredFields);
        return objectMap;
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
