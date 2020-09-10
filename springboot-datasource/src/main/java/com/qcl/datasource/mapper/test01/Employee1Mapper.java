package com.qcl.datasource.mapper.test01;

import com.qcl.datasource.bean.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工Mapper类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Mapper
public interface Employee1Mapper {

    /**
     * 插入员工(如果接口传入是对象参数不需要对象点属性直接获取属性即可)
     *
     * @param employee
     */
    @Insert("INSERT INTO `employee`(`lastName`, `email`, `gender`, `d_id`) VALUES (#{lastName}, #{email}, #{gender}, #{dId})")
    void insertEmp(Employee employee);

    /**
     * 查询所有员工
     *
     * @return
     */
    @Select("select * from employee")
    List<Employee> selectAll();
}
