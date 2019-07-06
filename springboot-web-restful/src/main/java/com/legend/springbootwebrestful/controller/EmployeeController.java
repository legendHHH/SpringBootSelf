package com.legend.springbootwebrestful.controller;


import com.legend.springbootwebrestful.dao.DepartmentDao;
import com.legend.springbootwebrestful.dao.EmployeeDao;
import com.legend.springbootwebrestful.entities.Department;
import com.legend.springbootwebrestful.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    //查询所有员工信息返回列表页面
    @GetMapping("/emps")
    public String list(Model model){
        //获取所有员工数据
        Collection<Employee> employees = employeeDao.getAll();
        //放在请求域中
        model.addAttribute("employees",employees);
        //thymeleaf默认会拼串
        //classpath:/templates/emp/xxx.html
        return "/emp/list";
    }


    //来到员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //去到员工添加页面时候,先查询部门列表
        Collection<Department> depts = departmentDao.getDepartments();
        model.addAttribute("depts",depts);
        return "emp/add";
    }


    //员工添加
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定;要求请求参数的名字和javaBean入参的对象里面的属性名一致
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("员工信息"+employee);

        employeeDao.save(employee);
        //来到员工列表
        //redirect:表示重定向到一个地址   /  代表当前项目路径
        //forward:表示转发到一个地址
        return "redirect:/emps";
    }


    @GetMapping(value = "/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,
                             Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);

        Collection<Department> depts = departmentDao.getDepartments();
        model.addAttribute("depts",depts);
        //添加和编辑页面二合一
        return "emp/add";
    }


    //修改员工  需要传过来员工id
    @PutMapping("/emp")
    public String updateEmp(Employee employee){
        System.out.println("新修改的员工"+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }


    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
