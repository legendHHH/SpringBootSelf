package com.legend.springboot.mapper;


import com.legend.springboot.bean.Employee;

import java.util.List;

//@Mapper或者@MapperScan将接口扫描装配到容器中
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> selectAll();

    public void insertEmp(Employee employee);
}
