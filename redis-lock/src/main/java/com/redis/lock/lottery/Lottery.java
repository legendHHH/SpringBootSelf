package com.redis.lock.lottery;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 抽奖程序
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
public class Lottery {

    /**
     * 抽奖的名单
     */
    static String[] QQ = {"legend", "Tony", "james", "lucy", "honey"};

    /**
     * 多少个人获奖
     */
    static int NUM = 1;

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        for (; ; ) {
            if (set.size() == NUM) {
                break;
            }
            int random = new SecureRandom().nextInt(QQ.length);
            set.add(QQ[random]);
        }
        Iterator iterator = set.iterator();
        System.out.println("************抽奖活动************");
        System.out.println("开奖人");
        System.out.println("开奖名单如下");
        while (iterator.hasNext()) {
            System.out.printf("中手机大奖的是:%s\r\n", iterator.next());
        }
        System.out.println("恭喜中奖,请领取");


    }

    public static String method() {
        System.out.println("hello");
        return null;
    }
}
