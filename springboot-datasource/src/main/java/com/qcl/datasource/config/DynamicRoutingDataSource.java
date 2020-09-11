package com.qcl.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态路由数据源管理
 *
 * 用户登陆,获取用户的地区,判断是028还是010根据这个获取对应的数据源进行操作对应的数据库
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Deprecated
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 根据一些规则获取到数据源
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.get();
    }
}
