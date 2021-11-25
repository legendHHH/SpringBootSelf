package com.qcl.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.mybatisplus.entity.Employee;
import com.qcl.mybatisplus.provider.EmployeeProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工Mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    @InsertProvider(type = EmployeeProvider.class, method = "insertListSql")
    void sqlInsert(List<Employee> list);

    void xmlBatchInsert(@Param("list") List<Employee> list);
}
