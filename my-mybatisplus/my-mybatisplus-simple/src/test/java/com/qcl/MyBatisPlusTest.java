package com.qcl;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.qcl.mapper.UserMapper;
import com.qcl.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * MyBatisPlusTest
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/8/7
 */
public class MyBatisPlusTest {

    @Test
    public void mybatisPlusTest() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

//        List<User> users = mapper.findAll();
        List<User> users = mapper.selectList(null);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void mybatisTest() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }


    }
}
