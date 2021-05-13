package com.qcl.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcl.mybatisplus.entity.User;
import com.qcl.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试
 * 利用SpringBootTest自带快捷测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisPlusApplication.class)
public class MyBatisPlusApplicationTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试乐观锁
     */
    @Test
    public void testOptimisticLock() {
        //根据id查询
        User user = userMapper.selectById(1392826562788720642L);
        user.setUserName("John");

        //修改
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1392821377689559042L);
        user.setUserName("legend");
        user.setAge(30);
        user.setEmail("737796231@qq.com");
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试新增
     */
    @Test
    public void testAdd() {
        User user = new User();
        user.setUserName("legend9");
        user.setAge(33);
        user.setEmail("737796231@qq.com");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    /**
     * 查询所有
     */
    @Test
    public void findAll() {
        System.out.println(userMapper.toString());
        //带上查询条件
        /*QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("username", "age").like("username", "Tom");
        List<User> userList = userMapper.selectList(queryWrapper);*/
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList.toString());
    }

}