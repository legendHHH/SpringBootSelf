package com.example.demo.reflection;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/6/20
 */
public class MyReflectUtil {
    /**
     * 获取对象关健字段名和字段值
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> getFieldNameAndValue(Object obj) {
        try {
            Map<String, Object> map = new HashMap<>(16);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                String name = f.getName();

                map.put(name, f.get(obj));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        SystemDeptCheckExistInfoBO systemDeptCheckExistInfoBO = new SystemDeptCheckExistInfoBO();
        systemDeptCheckExistInfoBO.setDeptCode("001");
        systemDeptCheckExistInfoBO.setAExistFlag("1");
        systemDeptCheckExistInfoBO.setBExistFlag("2");
        Map<String, Object> fieldNameAndValue = getFieldNameAndValue(systemDeptCheckExistInfoBO);
        System.out.println(JSONObject.toJSONString(fieldNameAndValue));
    }
}
