package com.qcl.clickhouse.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Java for ClickHouse连接工具类
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/8/3
 */
public class ClickhouseUtils {

    private static Connection connection = null;

    static {
        try {
            // 驱动包
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            // url路径
            String url = "jdbc:clickhouse://192.168.56.10:8123/system";

            // 账号
            String user = "default";
            // 密码
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from system.functions");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metaData.getColumnName(i) + ":" + resultSet.getString(i));
            }
        }
    }

}