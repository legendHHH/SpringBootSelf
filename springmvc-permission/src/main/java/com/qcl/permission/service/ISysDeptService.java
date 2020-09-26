package com.qcl.permission.service;

import com.qcl.permission.param.DeptParam;

/**
 * 部门service
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
public interface ISysDeptService {

    /**
     * 保存部门
     *
     * @param param
     */
    public void save(DeptParam param);

    /**
     * 更新部门接口
     *
     * @param param
     */
    public void update(DeptParam param);

    /**
     * 删除部门
     *
     * @param id
     */
    void delete(int id);
}
