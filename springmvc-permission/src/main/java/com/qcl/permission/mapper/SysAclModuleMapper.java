package com.qcl.permission.mapper;

import com.qcl.permission.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限模块mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysAclModuleMapper {
    /**
     * 删除数据根据主键
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据
     *
     * @param record
     * @return
     */
    int insert(SysAclModule record);

    /**
     * 有选择的插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(SysAclModule record);

    /**
     * 查询数据根据主键
     *
     * @param id
     * @return
     */
    SysAclModule selectByPrimaryKey(Integer id);

    /**
     * 有选择的更新根据主键
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysAclModule record);

    /**
     * 更新所有数据根据主键
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysAclModule record);

    /**
     * 获取子权限
     *
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

    /**
     * 批量更新Level
     *
     * @param aclModuleList
     */
    void batchUpdateLevel(@Param("aclModuleList") List<SysAclModule> aclModuleList);

    /**
     * 获取当前所有的权限模块
     *
     * @return
     */
    List<SysAclModule> getAllAclModule();
}