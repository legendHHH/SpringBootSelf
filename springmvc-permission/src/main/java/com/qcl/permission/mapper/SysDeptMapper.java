package com.qcl.permission.mapper;

import com.qcl.permission.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysDeptMapper {
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 插入部门包括空值
     *
     * @param record
     * @return
     */
    int insert(SysDept record);

    /**
     * 选择性插入部门不包括空值
     *
     * @param record
     * @return
     */
    int insertSelective(SysDept record);

    /**
     * 根据主键查询部门
     *
     * @param id
     * @return
     */
    SysDept selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 选择性更新部门信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysDept record);

    /**
     * 更新部门信息包括空值
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysDept record);

    /**
     * 获取所有部门信息
     *
     * @return
     */
    List<SysDept> getAllDept();

    /**
     * 获取子部门
     *
     * @param level
     * @return
     */
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    /**
     * 批量更新Level
     *
     * @param deptList
     */
    void batchUpdateLevel(@Param("deptList") List<SysDept> deptList);

    /**
     * 判断同一个层级下面已经存在相同的部门名字
     *
     * @param parentId
     * @param deptName
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    /**
     * 计算查询父节点
     *
     * @param deptId
     * @return
     */
    int countByParentId(@Param("deptId") int deptId);
}