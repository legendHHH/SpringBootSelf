//package com.legend.springbootmybatis.datasource2;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
///**
// * 动态数据源
// *
// * @author legend
// * @version 1.0
// * @description
// * @date 2022/1/7
// */
//public class DynamicDataSource extends AbstractRoutingDataSource {
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return DynamicDataSourceContextHolder.getDataSourceType();
//    }
//
//}