package com.legend;

import com.legend.mapper.IOrderMapper;
import com.legend.mapper.IUserMapper;
import com.legend.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

    /**
     * 一级缓存
     */
    @Test
    public void firstLevelCache() {
        //第一次查询id为1的用户
        User userById = userMapper.findUserById(1);

        //第二次查询id为1的用户
        User userById2 = userMapper.findUserById(1);
        //true
        System.out.println(userById == userById2);


        //更新用户  中间经过增删改操作，并进行了事务提交，就是刷新了一级缓存
        //clearCache(); 手动刷新一级缓存
        User user = new User();
        user.setId("1");
        user.setUsername("45678");
        userMapper.updateUser(user);

        User userById3 = userMapper.findUserById(1);
        //false
        System.out.println(userById == userById3);

        //手动提交事务
        //sqlSession.commit();
        //手动刷新一级缓存
        //sqlSession.clearCache();
    }

    /**
     * 二级缓存
     * 缓存的不是对象，是数据
     */
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
        user.setUsername("test二级缓存");
        userMapper3.updateUser(user);
        sqlSession3.commit();

        User user2 = userMapper2.findUserById(1);
        //第一次结果是false，第二次测试还是false
        System.out.println(user1 == user2);
    }
}
