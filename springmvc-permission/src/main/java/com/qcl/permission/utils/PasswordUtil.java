package com.qcl.permission.utils;


import java.util.Date;
import java.util.Random;

/**
 * 用户密码工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/27
 */
public class PasswordUtil {

    //可以使用的字符
    public final static String[] word = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    //可以使用的数字
    public final static String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    /**
     * 随机密码
     *
     * @return
     */
    public static String randomPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        //Random random = new Random(new Date().getTime());
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        //随机 8-10位之间
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            } else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
    }
}
