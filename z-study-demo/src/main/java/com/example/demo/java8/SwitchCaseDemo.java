package com.example.demo.java8;


import com.example.demo.java8.enums.StatusEnum;

/**
 * Switch case的使用  6种类型
 * 基本数据类型
 * 枚举类型
 * 包装数据类型
 *
 * @author legend
 */
public class SwitchCaseDemo {

    /**
     * 语法格式
     */
    public static void test0() {
        /*switch (expression) {
            case value:
                //语句
                break;//可选
            case value:
                //语句
                break;//可选

            //你可以有任意数量的case语句

            default://可选
                //语句
        }*/
    }

    /**
     * expression为基本数据类型
     * 常规写法
     *
     * @param value
     */
    private static void test(int value) {
        switch (value) {
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("1");
                break;
            case 3:
                System.out.println("1");
                break;
            case 4:
                System.out.println("1");
                break;
            case 5:
                System.out.println("1");
                break;
            case 6:
                System.out.println("0");
                break;
            case 7:
                System.out.println("0");
                break;
            default:
                System.out.println("-1");
        }
    }


    /**
     * expression为基本数据类型
     * 优化test1()
     *
     * @param value
     */
    private static void test2(int value) {
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("1");
                break;
            case 6:
            case 7:
                System.out.println("0");
                break;
            default:
                System.out.println("-1");
        }
    }


    /**
     * expression为包装类型的switch case
     */
    public void test3() {
        Integer value = 3;
        switch (value) {
            case 1:
                System.out.println("1");
                //语句
                break;//可选
            case 3:
                System.out.println("3");
                //语句
                break;//可选

            //你可以有任意数量的case语句

            default://可选
                //语句
                System.out.println("default");
        }
    }

    /**
     * expression为枚举类型的switch case
     */
    public static void test4() {
        //枚举
        StatusEnum status = StatusEnum.PROCESSING;
        switch (status) {
            case OPEN:
                System.out.println("OPEN");
                //语句
                break;//可选
            case PROCESSING:
                System.out.println("PROCESSING");
                //语句
                break;//可选
            case CLOSE:
                System.out.println("CLOSE");
                //语句
                break;//可选

            //你可以有任意数量的case语句

            default://可选
                //语句
                System.out.println("default");
        }
    }
}
