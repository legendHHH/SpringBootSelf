package com.legend.demo.test2.testablemock;

/**
 * TestableMock类
 * 增加一个类，调用任意方法、成员方法、静态方法：
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/2
 */
public class TestableMock {

    /**
     * 调用任意方法
     */
    public String commonMethod() {
        String result = " www ".trim() + "." + " legend".substring(1) + "www.legend.cn".startsWith(".com");
        System.out.println("commonMethod："+result);
        return result;
    }


    /**
     * 调用成员、静态方法
     */
    public String memberMethod(String s) {
        return "{ \"result\": \"" + innerMethod(s) + staticMethod() + "\"}";
    }

    private static String staticMethod() {
        return "WWW_LEGEND_CN";
    }

    private String innerMethod(String website) {
        return "our website is: " + website;
    }


}