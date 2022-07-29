package com.legend.mapper;

import com.legend.pojo.Order;
import com.legend.pojo.User;
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
 * @date 2022/7/29
 */
public interface IOrderMapper {
    /**
     * 查询所有用户、同时查询每个用户关联的订单信息
     *
     * @return
     */
    List<Order> findOrderAndUser();


    /**
     * 一对一注解开发：查询订单的同时还查询该订单所属的用户
     * one = @One(select ="namespace.id")
     * column="uid":传递的参数（com.legend.mapper.IUserMapper.findUserById）
     *
     * @return
     */
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",column = "uid", javaType = User.class,
                    one = @One(select = "com.legend.mapper.IUserMapper.findUserById"))
    })
    @Select("select * from orders ")
    List<Order> findOrderAndUserByAnnoation();


    /**
     * 根据用户id查询订单列表(配合com.legend.mapper.IUserMapper#findAllByAnnoation()方法查询数据)
     * @param uid
     * @return
     */
    @Select("select * from orders where uid = #{uid}" )
    public List<Order> findOrderByUid(Integer uid);

}
