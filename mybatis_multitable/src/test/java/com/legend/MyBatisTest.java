package com.legend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legend.mapper.IOrderMapper;
import com.legend.mapper.IUserMapper;
import com.legend.pojo.Order;
import com.legend.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * test
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/29
 */
public class MyBatisTest {

    private IUserMapper userMapper;
    private IOrderMapper orderMapper;

    @Before
    public void extractCommon() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //事务自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        userMapper = sqlSession.getMapper(IUserMapper.class);
        orderMapper = sqlSession.getMapper(IOrderMapper.class);
    }



    //================================基于mybatis注解开发================================
    @Test
    public void addUser() {
        User user = new User();
        user.setId("4");
        user.setUsername("4");
        user.setPassword("4");
        userMapper.addUser(user);

    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId("2");
        user.setUsername("测试数据");
        userMapper.updateUser(user);

    }

    @Test
    public void selectUser() {
        List<User> userList = userMapper.selectUser();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void delUser() {
        userMapper.deleteUser(4);

    }

    @Test
    public void oneToOneByAnnotation() {
        List<Order> orderList = orderMapper.findOrderAndUserByAnnoation();
        for (Order order : orderList) {
            System.out.println(order);
        }

    }

    @Test
    public void oneToManyByAnnotation() {
        List<User> userList = userMapper.findAllByAnnoation();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    //================================一对多、一对一、多对多查询================================


    @Test
    public void manyToMany() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        List<User> userList = userMapper.findAllUserAndRole();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void oneToMany() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        List<User> userList = userMapper.findAll();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void oneToOne() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orderAndUser = mapper.findOrderAndUser();
        for (Order order : orderAndUser) {
            System.out.println(order);
        }

    }
}
