package com.qcl.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充属性的接口
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/13
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * mp执行添加操作,这个方法执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("deleted", 0, metaObject);
    }

    /**
     * mp执行更新操作,这个方法执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
    }
}
