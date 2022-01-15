//package com.legend.springbootmybatis.datasource2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 动态数据源上下文
// *
// * @author legend
// * @version 1.0
// * @description
// * @date 2022/1/7
// */
//public class DynamicDataSourceContextHolder {
//
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
//    public static List<String> dataSourceIds = new ArrayList<>();
//
//    public static void setDataSourceType(String dataSourceType) {
//        contextHolder.set(dataSourceType);
//    }
//
//    public static String getDataSourceType() {
//        return contextHolder.get();
//    }
//
//    public static void clearDataSourceType() {
//        contextHolder.remove();
//    }
//
//    /**
//     * 判断指定DataSource当前是否存在
//     */
//    public static boolean containsDataSource(String dataSourceId) {
//        return dataSourceIds.contains(dataSourceId);
//    }
//}