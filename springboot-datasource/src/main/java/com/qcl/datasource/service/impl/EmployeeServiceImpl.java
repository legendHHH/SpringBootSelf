package com.qcl.datasource.service.impl;

import com.qcl.datasource.bean.Employee;
import com.qcl.datasource.mapper.test01.Employee1Mapper;
import com.qcl.datasource.mapper.test02.Employee2Mapper;
import com.qcl.datasource.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private Employee1Mapper employee1Mapper;

    @Autowired
    private Employee2Mapper employee2Mapper;

    /**
     * jta+atomic 把
     *
     * @param employee
     */
    @Override
    @Transactional
    public void addEmp(Employee employee) {
        employee1Mapper.insertEmp(employee);

        //演示事务一致性问题
        int i = 1 / 0;

        employee2Mapper.insertEmp(employee);
        System.out.println("执行完插入。。。。");
    }

    @Override
    public List<Employee> selectAll() {
        return employee1Mapper.selectAll();
    }
}
