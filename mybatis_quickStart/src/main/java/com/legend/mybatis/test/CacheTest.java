package com.legend.mybatis.test;

import com.legend.mybatis.dao.IUserDao;
import com.legend.mybatis.dao.UserDaoImpl;
import com.legend.mybatis.mapper.IOrderMapper;
import com.legend.mybatis.mapper.IUserMapper;
import com.legend.mybatis.pojo.Order;
import com.legend.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 缓存test
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/10
 */
public class CacheTest {
    private IUserMapper userMapper;
    private IOrderMapper orderMapper;
    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //SqlSession sqlSession = sqlSessionFactory.openSession(true);
        sqlSession = sqlSessionFactory.openSession(true);

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        userMapper = sqlSession.getMapper(IUserMapper.class);
        orderMapper = sqlSession.getMapper(IOrderMapper.class);
    }

    @Test
    public void firstLevelCache() {
        //第一次查询id为1的用户
        User userById = userMapper.findUserById(1);

        //更新用户  中间经过增删改操作，并进行了事务提交，就是刷新了一级缓存
        User user = new User();
        user.setId("1");
        user.setUsername("45678");
        userMapper.updateUser(user);
        //手动提交事务
        //sqlSession.commit();
        //手动刷新一级缓存
        //sqlSession.clearCache();

        //第一次查询id为1的用户
        User userById2 = userMapper.findUserById(1);

        System.out.println(userById == userById2);
    }

    @Test
    public void secondLevelCache() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        IUserMapper userMapper1 = sqlSession1.getMapper(IUserMapper.class);
        IUserMapper userMapper2 = sqlSession2.getMapper(IUserMapper.class);
        IUserMapper userMapper3 = sqlSession3.getMapper(IUserMapper.class);


        User user1 = userMapper1.findUserById(1);
        //清空一级缓存
        sqlSession1.close();

        //更新操作
        User user = new User();
        user.setId("1");
        user.setUsername("gggg");
        userMapper3.updateUser(user);
        sqlSession3.commit();

        User user2 = userMapper2.findUserById(1);
        System.out.println(user1 == user2);
    }
}
