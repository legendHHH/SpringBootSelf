package com.legend.springboot.mapper;

import com.legend.springboot.bean.Department;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;

/**
 * 指定这是一个操作数据库的mapper
 * MyBatis 流式查询接口
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/30
 */
//@Mapper
public interface DepartmentCursorMapper {

    @Select("select * from department limit #{limit}")
    Cursor<Department> getDeptList(@Param("limit") int limit);

}
