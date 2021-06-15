package com.qcl.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * 实现MySQL数据监听
 * Canal监听器测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/15
 */
@CanalEventListener
public class CanalDataEventListener {

    /**
     * 监听数据的增加
     * rowData.getBeforeColumnsList()：增加、修改
     * rowData.getAfterColumnsList()：删除、修改
     *
     * @param eventType 当前操作的类型  增加数据
     * @param rowData   发生变更的一行数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("onEventInsert");
        //增加之后的数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("列名：" + column.getName() + "------------变更的数据：" + column.getValue());
        }
    }

    /**
     * 监听数据的修改
     * rowData.getBeforeColumnsList()：增加、修改
     * rowData.getAfterColumnsList()：删除、修改
     *
     * @param eventType
     * @param rowData
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("onEventUpdate");
        //修改之前的数据
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("修改前列名：" + column.getName() + "------------修改前的数据：" + column.getValue());
        }

        //修改之后的数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("修改后列名：" + column.getName() + "------------修改后的数据：" + column.getValue());
        }

    }

    /**
     * 监听数据的删除
     * rowData.getBeforeColumnsList()：增加、修改
     * rowData.getAfterColumnsList()：删除、修改
     *
     * @param eventType
     * @param rowData
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("onEventDelete");
        //删除之前的数据
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("删除前列名：" + column.getName() + "------------删除前的数据：" + column.getValue());
        }
    }

    /**
     * 自定义监听数据
     * rowData.getBeforeColumnsList()：增加、修改
     * rowData.getAfterColumnsList()：删除、修改
     *
     * @param eventType
     * @param rowData
     */
    @ListenPoint(
            //监听类型
            eventType = {CanalEntry.EventType.DELETE, CanalEntry.EventType.INSERT},
            //指定监听的数据库
            schema = {"test"},
            //指定监控的表
            table = {"a_test"},
            //指定实例的地址
            destination = "example"
    )
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("onEventCustomUpdate");
        //自定义操作前的数据
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("自定义操作前列名：" + column.getName() + "------------自定义操作前的数据：" + column.getValue());
        }

        //自定义操作后的数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("自定义操作后列名：" + column.getName() + "------------自定义操作后的数据：" + column.getValue());
        }
    }
}
