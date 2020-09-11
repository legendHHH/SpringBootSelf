package com.qcl.datasource.config;

import com.qcl.datasource.bean.AreaKeyId;

/**
 * 动态数据源管理
 * 每一个请求过来都是一个线程去处理,所以说我们需要对每一个线程进行数据源控制
 *
 * 用户登陆,获取用户的地区,判断是028还是010根据这个获取对应的数据源进行操作对应的数据库
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Deprecated
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<AreaKeyId> currentDataSource = new ThreadLocal<AreaKeyId>();


    /**
     * 清除当前数据源
     */
    public static void clear(){
        currentDataSource.remove();
    }

    /**
     * 获取当前数据源
     */
    public static AreaKeyId get(){
        return currentDataSource.get();
    }

    /**
     * 设置当前数据源
     */
    public static void set(AreaKeyId areaKeyId){
        currentDataSource.set(areaKeyId);
    }
}
