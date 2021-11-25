package com.qcl.mybatisplus.provider;

import com.qcl.mybatisplus.entity.Employee;

import java.util.List;

/**
 * 员工Provider
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/25
 */
public class EmployeeProvider {

    public String insertListSql(List<Employee> list) {
        StringBuffer sqlList = new StringBuffer();

        sqlList.append(" INSERT INTO `tb_employee`(`id`, `last_name`, `gender`, `age`) VALUES ");
        for (int i = 0; i < list.size(); i++) {
            Employee employee = list.get(i);
            sqlList.append(" (").append(employee.getId()).append(",").append("'").append(employee.getLastName()).append("',").append(employee.getGender())
                    .append(",").append(employee.getAge()).append(")");
            if (i < list.size() - 1) {
                sqlList.append(",");
            }
        }
        return sqlList.toString();
    }
}