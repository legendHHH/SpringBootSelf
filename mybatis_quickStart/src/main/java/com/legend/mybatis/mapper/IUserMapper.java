package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * IUserMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public interface IUserMapper {

    /**
     * 查询所有用户、同时查询每个用户关联的订单信息
     *
     * @return
     */
    public List<User> findAll();

    public List<User> findAllUser();

    /**
     * 查询所有用户、同时查询每个用户关联的角色信息
     *
     * @return
     */
    public List<User> findAllUserAndRole();

    /**
     * 添加用户
     *
     * @param user
     */
    @Insert("insert into user values(#{id},#{username},#{password})")
    public void addUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    @Update("update user set username = #{username} where id = #{id}")
    public void updateUser(User user);

    /**
     * 查询用户
     *
     * @return
     */
    @Select("select * from user")
    public List<User> selectUser();

    /**
     * 删除用户
     *
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    public void deleteUser(Integer id);

}
