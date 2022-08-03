package com.legend.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legend.mybatis.dao.IUserDao;
import com.legend.mybatis.dao.UserDaoImpl;
import com.legend.mybatis.mapper.CityMapper;
import com.legend.mybatis.mapper.IOrderMapper;
import com.legend.mybatis.mapper.IUserMapper;
import com.legend.mybatis.pojo.City;
import com.legend.mybatis.pojo.Order;
import com.legend.mybatis.pojo.User;
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
 * @date 2022/4/9
 */
public class MyBatisTest {

    @Test
    public void test() throws Exception {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //构建者设计模式
        //2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生产sqlSession
        //默认开启一个事务，但是该事务不会自动提交。在进行增删改操作时，要手动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.sqlSession调用方法，查询所有selectList  查询单个：selectOne  添加：insert 修改：update  删除：delete
        List<User> u2 = sqlSession.selectList("com.legend.mybatis.dao.IUserDao.findAll");
        for (User user1 : u2) {
            System.out.println(user1);
        }
        sqlSession.close();
    }

    /**
     * 二级缓存===>一级缓存===>数据库
     *
     * @throws Exception
     */
    @Test
    public void codeAnalysis() throws Exception {
        //1.Resources读取配置文件，读成字节输入流，注意：还没解析
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析了配置文件，封装成Configuration对象并创建了DefauleSqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //以上就是1、2就是mybatis的初始化过程

        //3.生产了DefaultSqlSession实例对象，设置了事务不自动提交，完成了Executor对象的创建
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4.(1)根据statementId来从Configuration中大map集合中获取到了指定的MappedStatement对象
           //(2)将查询任务委派了executor执行器
        List<Object> objects = sqlSession.selectList("com.legend.mybatis.mapper.IUserMapper.findAllUser");


        //动态代理的形式源码分析
        //使用JDK动态代理对mapper接口产生代理对象
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        //代理对象调用接口中的任意方法，执行的都是动态代理中的invoke方法(org.apache.ibatis.binding.MapperProxy.invoke)
        List<User> all = userMapper.findAllUser();
        for (User user : all) {
            System.out.println(user);
        }

        //二级缓存源码分析测试(org.apache.ibatis.builder.xml.XMLMapperBuilder.configurationElement)
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        //二级缓存才会生效
        sqlSession.commit();
        List<Object> objects2 = sqlSession2.selectList("com.legend.mybatis.mapper.IUserMapper.findAllUser");
        sqlSession.close();
    }

    @Test
    public void test2() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //构建者设计模式
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //可以设置自动提交事务
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

        User user = new User();
        user.setId("90");
        user.setUsername("testInsert");
        user.setPassword("123456");
        sqlSession.insert("com.legend.mybatis.dao.IUserDao.saveUser", user);
        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void test3() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //构建者设计模式
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId("90");
        user.setUsername("testUpdate");
        sqlSession.update("com.legend.mybatis.dao.IUserDao.updateUser", user);
        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void test4() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //构建者设计模式
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("com.legend.mybatis.dao.IUserDao.deleteUser", 90);
        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void test5() throws Exception {
        IUserDao userDao = new UserDaoImpl();
        List<User> users = userDao.selectList();
        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void test6() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.selectList();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test7() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        int[] arr = {1, 2};
        List<User> users = mapper.findByIds(arr);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test8() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);

        List<Order> orders = mapper.findOrderAndUser();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void test9() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);

        //List<User> users = mapper.findAll();
        //List<User> users = mapper.findAllUser();
        List<User> users = mapper.findAllUserAndRole();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private IUserMapper userMapper;
    private IOrderMapper orderMapper;
    private CityMapper cityMapper;

    @Before
    public void method() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //使用jdk动态代理获得MyBatis框架⽣成的UserMapper接口的实现类
        userMapper = sqlSession.getMapper(IUserMapper.class);
        orderMapper = sqlSession.getMapper(IOrderMapper.class);
        cityMapper = sqlSession.getMapper(CityMapper.class);
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setId("4");
        user.setUsername("4");
        user.setPassword("4");
        userMapper.addUser(user);

    }

    @Test
    public void oneToOne() {
        List<Order> orderAndUserPro = orderMapper.findOrderAndUserPro();
        for (Order order : orderAndUserPro) {
            System.out.println(order);
        }

    }

    @Test
    public void oneToMany() {
        List<User> userList = userMapper.findAllPro();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void manyToMany() {
        List<User> userList = userMapper.findAllUserAndRolePro();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void pageHelperTest() {
        PageHelper.startPage(1, 2);
        List<User> userList = userMapper.findAllUser();
        for (User user : userList) {
            System.out.println(user);
        }

        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        System.out.println("总条数：" + userPageInfo.getTotal());
        System.out.println("总页数：" + userPageInfo.getPages());
        System.out.println("当前页：" + userPageInfo.getPageNum());
        System.out.println("每页显示的条数：" + userPageInfo.getPageSize());
    }

    @Test
    public void tkMapperTest() {
        City city = cityMapper.selectByPrimaryKey(1L);
        System.out.println(city);

        City city1 = new City();
        city1.setCityName("tkMapper");
        int count = cityMapper.insertSelective(city1);
        System.out.println(count);

        //example
        Example example = new Example(City.class);
        example.createCriteria().andEqualTo("id", 1);
        List<City> cityList = cityMapper.selectByExample(example);
        for (City city2 : cityList) {
            System.out.println(city2);
        }
    }
}
