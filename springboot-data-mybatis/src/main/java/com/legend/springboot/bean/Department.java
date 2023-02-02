package com.legend.springboot.bean;

import com.legend.springboot.myencrypt.Encrypt;

public class Department {

    private Integer id;
    private String departmentName;
    private Encrypt deptNameEncrypt;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Encrypt getDeptNameEncrypt() {
        return deptNameEncrypt;
    }

    public void setDeptNameEncrypt(Encrypt deptNameEncrypt) {
        this.deptNameEncrypt = deptNameEncrypt;
    }
}
