package com.qcl.permission.mapper;

import com.qcl.permission.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysUserMapper {
    /**
     * 删除根据主键
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 插入用户
     *
     * @param record
     * @return
     */
    int insert(SysUser record);

    /**
     * 有选择性插入用户
     *
     * @param record
     * @return
     */
    int insertSelective(SysUser record);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 根据主键有选择性更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 更新根据主键
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 查询用户
     *
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * 校验邮箱
     *
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    /**
     * 校验电话
     *
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    /**
     * 校验部门
     *
     * @param deptId
     * @return
     */
    int countByDeptId(@Param("deptId") int deptId);

    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);

    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<SysUser> getAll();
}