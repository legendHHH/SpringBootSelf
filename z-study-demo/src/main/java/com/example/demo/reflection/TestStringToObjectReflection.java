package com.example.demo.reflection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 测试JSONString转实体类校验为空
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/12
 */
public class TestStringToObjectReflection {

    public static void main(String[] args) {
        //{"goodsList":"[{\"supplierId\":\"456789\"}]","id":"1"}
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 123);
        jsonObject.put("goodsList", Arrays.asList(new GdOrderGoodsVo("25566")));
        GdOrderVo gdOrderVo = JSON.parseObject(JSON.toJSONString(jsonObject), GdOrderVo.class);
        System.out.println(gdOrderVo);

        checkParams(gdOrderVo);
    }

    /**
     * 空值判断
     *
     * @param obj
     */
    public static boolean checkParams(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String name = field.getName();
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (annotation != null) {
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value == null || "".equals(value.toString())) {
                    System.out.println(name + ":::::" + annotation.message());
                    return false;
                }
            }
        }
        return true;
    }
}
