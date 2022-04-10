package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.User;
import org.apache.ibatis.annotations.*;

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

    /**
     * 一对多注解实现查询用户的同时还查询订单列表
     *
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "orderList", column = "id", javaType = List.class,
                    many = @Many(select = "com.legend.mybatis.mapper.IOrderMapper.findOrderByUid"))
    })
    @Select("select * from user")
    public List<User> findAllPro();

    public List<User> findAllUser();

    /**
     * 查询所有用户、同时查询每个用户关联的角色信息
     *
     * @return
     */
    public List<User> findAllUserAndRole();

    /**
     * 多对多查询所有用户、同时查询每个用户关联的角色信息
     *
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "com.legend.mybatis.mapper.IRoleMapper.findRoleByUid"))
    })
    @Select("select * from user")
    public List<User> findAllUserAndRolePro();

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

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Select({"select * from user where id = #{id}"})
    public User findUserById(Integer id);
}
