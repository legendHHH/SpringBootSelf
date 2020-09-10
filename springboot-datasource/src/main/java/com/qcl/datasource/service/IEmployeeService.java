package com.qcl.datasource.service;

import com.qcl.datasource.bean.Employee;

import java.util.List;

/**
 * 员工Service类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
public interface IEmployeeService {
    /**
     * 添加员工
     *
     * @param employee
     */
    void addEmp(Employee employee);

    /**
     * 查询所有员工
     *
     * @return
     */
    List<Employee> selectAll();
}