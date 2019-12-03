package com.legend.springcache.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    /**
     * 使用连接池重构用户查询函数
     */
    public void findAllUsers() {
        try {
            //1.使用连接池建立数据库连接
            TestDataSource dataSource = new TestDataSource();
            Connection conn = dataSource.getConnection();
            //2.创建状态
            Statement state = conn.createStatement();
            //3.查询数据库并返回结果
            ResultSet result = state.executeQuery("select * from users");
            //4.输出查询结果
            while (result.next()) {
                System.out.println(result.getString("email"));
            }
            //5.断开数据库连接
            result.close();
            state.close();
            //6.归还数据库连接给连接池
            dataSource.releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
