package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 使用栈实现后缀表达式的计算
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/16
 */
public class Exam {

    /**
     * 将字符串转换成List
     *
     * @param suffixExpression
     * @return
     */
    public static List<String> stringToList(String suffixExpression) {
        List<String> list = new ArrayList<>();
        String[] sts = suffixExpression.split(" ");
        for (String s : sts) {
            list.add(s);
        }
        return list;
    }

    public static int calc(List<String> list) {
        Stack<String> stack = new Stack<>();
        int res = 0;
        for (String item : list) {
            if (item.matches("\\d+")) {
                stack.push(item);
            } else {
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                if (item.equals("+")) {
                    res = num2 + num1;
                } else if (item.equals("-")) {
                    res = num2 - num1;
                } else if (item.equals("*")) {
                    res = num2 * num1;
                } else if (item.equals("/")) {
                    res = num2 / num1;
                } else {
                    throw new RuntimeException("运算符有误~~~~~~~~~~~~~~");
                }
                stack.push(res + "");
            }
        }
        return Integer.parseInt(stack.pop());

    }

    public static void showStack(Stack stack) {
        System.out.println("总长度：" + stack.size());
        if (stack.size() == 0) {
            System.out.println("栈空,不能遍历");
            return;
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

    }

    public static void main(String[] args) {
        //(1)创建一个栈
        Stack<Integer> stack = new Stack<>();
        //向栈中存放元素
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.insertElementAt(5, 2);
        stack.remove(2);
        System.out.println("获取栈顶元素：" + stack.peek());
        System.out.println("弹出栈顶元素：" + stack.pop());
        showStack(stack);


        //(2)后缀表达式
        List<String> list = stringToList("3 4 + 5 * 6 -");
        int cal = calc(list);
        System.out.println("后缀表达式计算值：" + cal);

    }
}
