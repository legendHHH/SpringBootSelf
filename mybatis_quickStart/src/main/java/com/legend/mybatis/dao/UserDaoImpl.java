package com.legend.mybatis.dao;

import com.legend.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 传统开发方式
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
@Deprecated
public class UserDaoImpl implements IUserDao {

    @Override
    public List<User> selectList() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //构建者设计模式
        //2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生产sqlSession
        //默认开启一个事务，但是该事务不会自动提交。在进行增删改操作时，要手动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.sqlSession调用方法，查询所有selectList  查询单个：selectOne  添加：insert 修改：update  删除：delete
        List<User> u2 = sqlSession.selectList("user.findAll");
        sqlSession.close();
        return u2;
    }

    @Override
    public User selectOne(User user) {
        return null;
    }

    @Override
    public User findByCondition(User user) {
        return null;
    }

    @Override
    public List<User> findByIds(int[] arr) {
        return null;
    }
}
