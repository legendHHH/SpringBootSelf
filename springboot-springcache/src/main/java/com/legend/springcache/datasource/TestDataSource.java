package com.legend.springcache.datasource;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 数据库连接池原理分析
 * @author legend
 */
public class TestDataSource implements DataSource {
    /**
     * 链表--实现栈结构
     */
    private LinkedList<Connection> dataSources = new LinkedList<>();

    /**
     * 初始化连接数量
     */
    public TestDataSource() {
        for (int i = 0; i < 10; i++) {
            try {
                //1、装载sqlserver驱动对象
                DriverManager.registerDriver(new SQLServerDriver());
                //2、通过JDBC建立数据库连接
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=customer", "sa", "123");
                //3、将连接加入连接池中
                dataSources.add(con);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        // 删除第一个连接返回
        final Connection conn = dataSources.removeFirst();
        return conn;
    }


    /**
     * 将连接放回连接池
     *
     * @param conn
     */
    public void releaseConnection(Connection conn) {
        dataSources.add(conn);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
