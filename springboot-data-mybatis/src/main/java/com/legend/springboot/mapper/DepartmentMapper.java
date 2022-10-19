package com.legend.springboot.mapper;


import com.legend.springboot.bean.Department;
import com.legend.springboot.myencrypt.Encrypt;
import org.apache.ibatis.annotations.*;

/**
 * 指定这是一个操作数据库的mapper
 * 注解版
 */
//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public Department deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName}) ")
    public int insertDept(Department departmentName);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(department_name, dept_name_encrypt) values(#{departmentName},#{deptNameEncrypt}) ")
    public int insertDeptEncrypt(Department departmentName);

    //@Select("select * from department where dept_name_encrypt=#{deptNameEncrypt}")
    public Department getDeptByIdDecrypt(@Param("id") Integer id, @Param("deptNameEncrypt") Encrypt deptNameEncrypt);

    @Update("update department set department_name=#{deptmentName} where id=#{id}")
    public Department updateDept(Department department);
}
