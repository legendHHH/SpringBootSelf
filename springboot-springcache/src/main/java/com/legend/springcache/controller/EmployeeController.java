package com.legend.springcache.controller;


import com.legend.springcache.entity.Employee;
import com.legend.springcache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 缓存管理器(解决数据被修改没有同步更新的问题)
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * ehcache缓存测试接口
     * http://localhost:8080/all/emp
     *
     * @return
     */
    @GetMapping("/all/emp")
    public Object getAllEmp() {
        List<Employee> employeeList = employeeMapper.selectAll();
        return employeeList;
    }

    /**
     * 更新员工数据接口测试ehcache缓存
     * http://localhost:8080/update/emp?id=1&gender=999
     *
     * @return
     */
    @GetMapping("/update/emp")
    @ResponseBody
    public Object updateEmp(Employee employee) {
        employeeMapper.updateEmp(employee);

        //缓存管理器用于更新缓存里面的数据
        Cache cacheManagerCache = cacheManager.getCache("cloud_employee");
        cacheManagerCache.clear();
        return "更新成功";
    }

}
