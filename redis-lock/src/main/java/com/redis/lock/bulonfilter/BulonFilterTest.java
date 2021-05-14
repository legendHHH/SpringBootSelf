package com.redis.lock.bulonfilter;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 基于guava实现布隆过滤器
 * 布隆过滤器本质上是一种比较巧妙的概率型数据结构，用来告诉我们某个东西一定不存在或可能存在，特点是高效的插入和查询，但不支持删除
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/14
 */
public class BulonFilterTest {

    /**
     * 预计要插入多少数据
     */
    private static int size = 1000000;

    /**
     * 期望的误判率
     */
    private static double fpp = 0.01;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public static void main(String[] args) {
        // 插入数据
        for (int i = 0; i < 11; i++) {
            //插入元素
            bloomFilter.put(i);
        }
        int count = 0;
        for (int i = 10; i < 20; i++) {
            //判定元素是否存在
            if (bloomFilter.mightContain(i)) {
                count++;
            }
            //ordinal方法（可以理解为枚举类中那个默认方法）
        }
        String str = "hhhh";
        String checkNotNull = Preconditions.checkNotNull(str);
        System.out.println("总共的误判数：" + count);
        System.out.println("checkNotNull：" + checkNotNull);
    }
}
