package com.qcl.permission.service;


import com.qcl.permission.model.SysUser;
import com.qcl.permission.param.UserParam;

import java.util.List;

/**
 * 用户service
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/27
 */
public interface ISysUserService {


    /**
     * 保存用户
     *
     * @param param
     */
    public void save(UserParam param);

    /**
     * 更新用户
     *
     * @param param
     */
    public void update(UserParam param);

    /**
     * 获取所有用户
     *
     * @return
     */
    public List<SysUser> getAll();

    /**
     * 查询用户
     *
     * @param username
     * @return
     */
    SysUser findByKeyword(String username);
}
