package com.legend.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDataJdbcApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() throws Exception{
        //class org.apache.tomcat.jdbc.pool.DataSource
        System.out.println(""+dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(""+connection);
        connection.close();

    }

}
