package com.legend.springbootmybatis.datasource;

import com.legend.springbootmybatis.holder.DBContextHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * 获取路由Key
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/22
 */
@Configuration
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}