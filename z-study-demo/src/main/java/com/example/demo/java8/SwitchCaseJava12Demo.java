package com.example.demo.java8;


import com.example.demo.java8.enums.Status;

/**
 * Java12种的Switch case写法
 * (支持箭头方法)
 *
 * @author legend
 */
public class SwitchCaseJava12Demo {

    /**
     * Java 12 之前是这样用的
     *
     * @param status
     */
    private static void testSwitch1(Status status) {
        int result = 0;
        switch (status) {
            case OPEN:
                result = 1;
                break;
            case PROCESS:
                result = 2;
                break;
            case PENDING:
                result = 2;
                break;
            case CLOSE:
                result = 3;
                break;
            default:
                throw new RuntimeException("状态不正确");
        }
        System.out.println("result is " + result);
    }

    /**
     * Java 12 后可以这样用
     *
     * @param status
     */
    private static void testSwitch2(Status status) {
        //Java12的var
        /*var result = switch (status) {
            case OPEN -> 1
                ;
            case PROCESS,PENDING -> 2;
            case CLOSE -> 3
                ;
            default ->throw new RuntimeException("状态不正确");
        } ;
        System.out.println("result is " + result);*/
    }

    private static void testSwitch3(Status status) {
        /*var result = switch (status) {
            case OPEN -> {
                break 1;
            }
            case PROCESS, PENDING -> {
                break 2;
            }
            case CLOSE -> {
                break 3;
            }
            default -> {
                break 5;
            }
        };
        System.out.println("result is " + result);*/
    }

    public static void main(String[] args) {
        testSwitch1(Status.CLOSE);
    }
}
