package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.Order;
import com.legend.mybatis.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * IOrderMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public interface IOrderMapper {

    /**
     * 查询订单的同时还查询该订单所属的用户
     *
     * @return
     */
    public List<Order> findOrderAndUser();

    /**
     * 一对一注解实现查询订单的同时还查询该订单所属的用户
     *
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "total", column = "total"),
            //column 就是主表(orders)和子表(user)的关联外键字段，也就是传入子表的参数字段
            @Result(property = "user", column = "uid", javaType = User.class,
                    //select=namespace.id
                    one = @One(select = "com.legend.mybatis.mapper.IUserMapper.findUserById"))
    })
    @Select("select * from orders")
    public List<Order> findOrderAndUserPro();

    /**
     * 根据用户id查询订单列表
     *
     * @param uid
     * @return
     */
    @Select("select * from orders where uid = #{uid}")
    public List<Order> findOrderByUid(Integer uid);
}
