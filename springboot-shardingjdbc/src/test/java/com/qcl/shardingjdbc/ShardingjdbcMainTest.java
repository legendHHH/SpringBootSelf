package com.qcl.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qcl.shardingjdbc.entity.Course;
import com.qcl.shardingjdbc.entity.Udict;
import com.qcl.shardingjdbc.mapper.CourseMapper;
import com.qcl.shardingjdbc.mapper.UdictMapper;
import com.qcl.shardingjdbc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 本地测试启动类
 *
 * @author lgend
 * @version 1.0
 * @description
 * @date 2021/6/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcMainTest {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UdictMapper udictMapper;


    //==========测试公共表==========
    @Test
    public void addDict(){
        Udict udict = new Udict();
        udict.setUstatus("NORMAL2");
        udict.setUvalues("正常2");
        int res = udictMapper.insert(udict);
        System.out.println(res);
    }

    @Test
    public void updateDict(){
        QueryWrapper<Udict> udictQueryWrapper = new QueryWrapper<>();
        udictQueryWrapper.eq("dictid", 612770118648201217L);
        Udict udict = udictMapper.selectOne(udictQueryWrapper);
        udict.setName("我是公共表,正在测试修改.....");
        int res = udictMapper.updateById(udict);
        System.out.println(res);
    }

    @Test
    public void deleteDict(){
        QueryWrapper<Udict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dictid", 1405894446456799233L);
        udictMapper.delete(queryWrapper);
    }


    //==========测试水平分库==========
    @Test
    public void addCourseDb(){
        for (int i = 0; i < 100; i++) {
            Course course = new Course();
            //分库根据userId
            course.setUserId(100L + i);
            course.setCname("Java Demo"+ i);
            course.setCstatus("Normal"+ i);
            int res = courseMapper.insert(course);
            System.out.println(res);
        }
    }

    @Test
    public void findCourseDb(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 611915248579903488L);
        queryWrapper.eq("user_id", 100L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }

    //==========测试水平分表==========


    @Test
    public void addCourse(){
        for (int i = 0; i < 100; i++) {
            Course course = new Course();
            course.setUserId(100L + i);
            course.setCname("Java开发"+ i);
            course.setCstatus("NORMAL"+ i);
            int res = courseMapper.insert(course);
            System.out.println(res);
        }
    }

    @Test
    public void findCourse(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 611915248579903488L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }
}
