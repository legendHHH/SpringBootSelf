package com.example.demo.java8;

import com.example.demo.java8.interfaces.MyInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author legend
 */
public class LambdaDemo {

    private static Logger log = LoggerFactory.getLogger(LambdaDemo.class);

    public static void main(String[] args) {
        //传统写法
        noParam();
        //Lambda语法
        noParamUseLambda();

        Map<String, String> map = new HashMap<>();
        map.put("a", "aa");
        map.put("b", "bb");
        map.put("legend", "HAND");
        //传统写法
        mapForeach(map);
        //Lambda语法
        mapForeachUseLambda(map);


        //ABC
        log.info("{}", toUpperString(str -> str.toUpperCase(), "abc"));
        System.out.println(toUpperString(str -> str.toUpperCase(), "abc"));

        //匿名内部类的写法
        System.out.println(toUpperString(new MyInterface<String>() {
            @Override
            public String getValue(String s) {
                return s;
            }
        }, "abc"));


        //匿名内部类的写法
        System.out.println(toUpperString1(new MyInterface<String>() {
            @Override
            public String getValue(String s) {
                return s;
            }
        }, "abc"));

        //使用内置的函数式接口的lambda写法
        System.out.println(toUpperString(str -> str.toUpperCase(), "abc"));
    }

    /**
     * 内置函数式接口
     *
     * @param mn
     * @param str
     * @return
     */
    public static String toUpperString1(Function<String, String> mn, String str) {
        return mn.apply(str);
    }

    /**
     * 定义的函数接口
     *
     * @param mn
     * @param str
     * @return
     */
    public static String toUpperString1(MyInterface<String> mn, String str) {
        return mn.getValue(str);
    }


    /**
     * 转换大写字母(自定义函数式接口)
     *
     * @param interStr
     * @param str
     */
    public static String toUpperString(MyInterface<String> interStr, String str) {
        return interStr.getValue(str);
    }


    /**
     * 遍历map集合
     *
     * @param map
     */
    public static void mapForeach(Map<String, String> map) {
        //使用增强for的方式来遍历hashMap
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "....." + entry.getValue());
        }
        System.out.println();
    }

    public static void mapForeachUseLambda(Map<String, String> map) {
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }


    /**
     * 空参数
     */
    public static void noParam() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world by xx");
            }

        }).start();
    }

    public static void noParamUseLambda() {
        //使用Lambda表达式创建线程
        new Thread(() -> System.out.println("hello world by lambda")).start();
    }
}
