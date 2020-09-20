package com.qcl.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcl.mybatisplus.entity.Employee;
import com.qcl.mybatisplus.mapper.EmployeeMapper;
import com.qcl.mybatisplus.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * 服务类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
