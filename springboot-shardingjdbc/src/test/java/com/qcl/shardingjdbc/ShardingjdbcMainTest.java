package com.qcl.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcl.shardingjdbc.entity.Course;
import com.qcl.shardingjdbc.mapper.CourseMapper;
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
