package com.example.demo.reflection;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemDeptCheckExistInfoBO implements Serializable {

    private String deptCode;
    private String aExistFlag;
    private String bExistFlag;

}