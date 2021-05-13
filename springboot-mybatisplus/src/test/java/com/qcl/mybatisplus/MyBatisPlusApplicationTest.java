package com.qcl.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qcl.mybatisplus.entity.User;
import com.qcl.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 测试根据简单条件删除
     */
    @Test
    public void testDeleteByMap() {
        //根据id删除
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_name", "legend9999");
        int result = userMapper.deleteByMap(columnMap);
        System.out.println(result);
    }

    /**
     * 测试根据ids批量删除
     */
    @Test
    public void testDeleteBatchIds() {
        //根据id删除
        int result = userMapper.deleteBatchIds(Arrays.asList(1392827352362381313L));
        System.out.println(result);
    }

    /**
     * 测试根据id删除
     */
    @Test
    public void testDeleteById() {
        //根据id删除
        int result = userMapper.deleteById(1392831124501172225L);
        System.out.println(result);
    }

    /**
     * 测试简单分页查询
     */
    @Test
    public void testSelectPage() {
        //分页查询(需要添加分页插件)
        Page<User> page = new Page<>(0, 5);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> userList = userPage.getRecords();
        System.out.println(userList.toString());
        System.out.println("总页数：" + userPage.getPages());
        System.out.println("当前页：" + userPage.getCurrent());
        System.out.println("总条数：" + userPage.getTotal());
        System.out.println("是否有上一页：" + userPage.hasPrevious());
        System.out.println("是否有下一页：" + userPage.hasNext());
    }

    /**
     * 测试简单条件查询
     */
    @Test
    public void testSelectByMap() {
        //根据条件查询
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_name", "John");
        columnMap.put("age", "33");
        List<User> userList = userMapper.selectByMap(columnMap);
        System.out.println(userList.toString());
    }

    /**
     * 测试批量id查询
     */
    @Test
    public void testSelectBatchIds() {
        //根据id查询
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        System.out.println(userList.toString());
    }

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