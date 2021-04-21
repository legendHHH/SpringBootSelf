package com.legend.es.utils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Bean和Map或者List之间互转工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/15
 */
public class BeanMapUtil {

    /**
     * map转对象
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(map.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, new Date(Long.parseLong(datetimestamp)));
                    }
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * List<Bean> 转 List<Map>
     *
     * @param list
     * @return
     */
    public static <T> List<Map<String, Object>> listBeanConvertToListMap(List<T> list) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        try {
            for (T t : list) {
                Field[] fields = getAllFields(t);
                Map<String, Object> m = new HashMap<String, Object>();
                for (Field field : fields) {
                    try {
                        String keyName = field.getName();
                        if (keyName.equals("serialVersionUID")) {
                            continue;
                        }
                        PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                        // 获得getter方法
                        Method getMethod = pd.getReadMethod();
                        // 执行get方法返回一个Object
                        Object o = getMethod.invoke(t);
                        //需要去掉属性值为空的属性
                        if (o != null) {
                            m.put(keyName, o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                listMap.add(m);
            }
            return listMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有属性 包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 获取利用反射获取类里面的值和名称(排除为空的属性不在map里面)
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>(16);
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            Object value = field.get(obj);
            if (value != null) {
                map.put(fieldName, value);
            }
        }
        return map;
    }
}
