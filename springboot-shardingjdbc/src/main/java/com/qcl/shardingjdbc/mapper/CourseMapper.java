package com.qcl.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.shardingjdbc.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/16
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}