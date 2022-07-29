package com.legend.mapper;

import com.legend.pojo.User;
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

    /**
     * 根据id查询用户(配合com.legend.mapper.IOrderMapper#findOrderAndUserByAnnoation()方法查询数据)
     *
     * @param id
     * @return
     */
    @Select({"select * from user where id = #{id}"})
    public User findUserById(Integer id);

    /**
     * 一对多注解开发：查询所有用户、同时查询每个用户关联的订单信息
     *
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "orderList", column = "id", javaType = List.class,
                    many = @Many(select = "com.legend.mapper.IOrderMapper.findOrderByUid"))
    })
    @Select("select * from user")
    public List<User> findAllByAnnoation();


    /**
     * 多对多注解开发：查询所有用户、同时查询每个用户关联的角色信息
     *
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "com.legend.mapper.IRoleMapper.findRoleByUid"))
    })
    @Select("select * from user")
    public List<User> findAllUserAndRoleByAnnoation();
}
