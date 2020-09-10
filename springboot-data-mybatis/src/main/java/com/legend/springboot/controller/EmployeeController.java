package com.legend.springboot.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legend.springboot.bean.Employee;
import com.legend.springboot.mapper.EmployeeMapper;
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
    private EmployeeMapper employeeMapper;


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
        List<Employee> employeeList = employeeMapper.selectAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return pageInfo;
    }


}
