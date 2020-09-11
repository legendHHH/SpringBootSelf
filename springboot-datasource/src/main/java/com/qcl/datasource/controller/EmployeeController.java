package com.qcl.datasource.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcl.datasource.bean.Employee;
import com.qcl.datasource.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@RestController
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;


    /**
     * pageHelper分页参数测试接口
     * http://localhost:8080/all/emp?pageNum=1&pageSize=2
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/all/emp")
    public Object getAllEmp(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> employeeList = employeeService.selectAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return pageInfo;
    }


    /**
     * 添加员工
     * http://localhost:8080/addEmp?gender=1
     *
     * com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Failed to get a connection using the URL 'null'.
     * 出现异常 降低版本有办法解决
     *
     * @param employee
     * @return
     */
    @GetMapping("/addEmp")
    public Employee insertEmp(Employee employee) {
        System.out.println("hello");
        employee.setLastName("qqcom");
        employee.setEmail("7477@qq.com");
        employee.setdId(888);
        employeeService.addEmp(employee);
        return employee;
    }

}
