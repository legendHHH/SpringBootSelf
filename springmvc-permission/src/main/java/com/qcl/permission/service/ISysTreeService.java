package com.qcl.permission.service;

import com.qcl.permission.dto.DeptLevelDto;

import java.util.List;

/**
 * 部门层级树结构
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
public interface ISysTreeService {

    /**
     * 部门树
     *
     * @return
     */
    public List<DeptLevelDto> deptTree();

}
