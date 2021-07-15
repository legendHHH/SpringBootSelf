package com.example.demo.pagehelper;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 手动分页List数据
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/15
 */
public class PageTest {


    public static void main(String[] args) {
        method(1, 15);
        method(2, 3);
        method(3, 3);
        method(4, 3);
        method(5, 3);
        method(6, 3);

        String a = null;
        Objects.requireNonNull(a);
    }

    public static List<Integer> method(int pageNo, int pageSize) {
        List<Integer> mainFormProductList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 21, 22);
        //实际查询的productId
        List<Integer> realMainFormProductList = new ArrayList<>();

        int productIdSize = mainFormProductList.size();
        System.out.println("总条数：" + productIdSize);
        for (int i = (pageNo - 1) * pageSize; i < Math.min(mainFormProductList.size(), pageNo * pageSize); i++) {
            realMainFormProductList.add(mainFormProductList.get(i));
        }

        System.out.println("第" + pageNo + "的数据：" + JSONObject.toJSONString(realMainFormProductList));
        return realMainFormProductList;
    }
}
