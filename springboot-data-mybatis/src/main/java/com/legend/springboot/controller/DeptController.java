package com.legend.springboot.controller;


import com.legend.springboot.bean.Department;
import com.legend.springboot.bean.Employee;
import com.legend.springboot.mapper.DepartmentCursorMapper;
import com.legend.springboot.mapper.DepartmentMapper;
import com.legend.springboot.mapper.EmployeeMapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class DeptController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentCursorMapper departmentCursorMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping("/deptCursor3/{limit}")
    @Transactional
    public List<Department> getDepartmentCursor3(@PathVariable("limit") Integer limit) throws IOException {
        List<Department> departmentList = new ArrayList<>();

        //会报错java.lang.IllegalStateException: A Cursor is already closed.
        /*try (Cursor<Department> cursor = departmentCursorMapper.getDeptList(limit)) {
            // 2
            cursor.forEach(foo -> {
                System.out.println(foo.getDepartmentName());
            });
        }*/

        //方案三：@Transactional 注解
        try (Cursor<Department> cursor = departmentCursorMapper.getDeptList(limit)) {
            Iterator<Department> iterator = cursor.iterator();
            while (iterator.hasNext()) {
                Department next = iterator.next();
                departmentList.add(next);
            }
        }

        return departmentList;
    }


    @GetMapping("/deptCursor2/{limit}")
    public List<Department> getDepartmentCursor2(@PathVariable("limit") Integer limit) throws IOException {
        List<Department> departmentList = new ArrayList<>();

        //会报错java.lang.IllegalStateException: A Cursor is already closed.
        /*try (Cursor<Department> cursor = departmentCursorMapper.getDeptList(limit)) {
            // 2
            cursor.forEach(foo -> {
                System.out.println(foo.getDepartmentName());
            });
        }*/

        //方案二：TransactionTemplate
        transactionTemplate.execute(status -> {
            try (Cursor<Department> cursor = departmentCursorMapper.getDeptList(limit)) {
                Iterator<Department> iterator = cursor.iterator();
                while (iterator.hasNext()) {
                    Department next = iterator.next();
                    departmentList.add(next);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        });

        return departmentList;
    }

    @GetMapping("/deptCursor/{limit}")
    public List<Department> getDepartmentCursor(@PathVariable("limit") Integer limit) throws IOException {
        List<Department> departmentList = new ArrayList<>();

        //会报错java.lang.IllegalStateException: A Cursor is already closed.
        /*try (Cursor<Department> cursor = departmentCursorMapper.getDeptList(limit)) {
            // 2
            cursor.forEach(foo -> {
                System.out.println(foo.getDepartmentName());
            });
        }*/

        //方案一：SqlSessionFactory
        SqlSession sqlSession = sqlSessionFactory.openSession(); // 1
        Cursor<Department> cursor = sqlSession.getMapper(DepartmentCursorMapper.class).getDeptList(limit);
        Iterator<Department> iterator = cursor.iterator();
        while (iterator.hasNext()) {
            Department next = iterator.next();
            System.out.println("输出：" + next);
            departmentList.add(next);
        }
        return departmentList;
    }

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return departmentMapper.getDeptById(id);
    }

    @GetMapping("/dept")
    public Department insertDept(Department department){
        departmentMapper.insertDept(department);
        return department;
    }

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
       return employeeMapper.getEmpById(id);
    }


}
