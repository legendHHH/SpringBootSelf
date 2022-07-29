package com.legend.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public class User implements Serializable {
    private String id;
    private String username;
    private String password;

    /**
     * 一个用户有多个角色
     */
    private List<Role> roleList = new ArrayList<>();

    /**
     * 一个用户有多个订单
     */
    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public User() {
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleList=" + roleList +
                ", orderList=" + orderList +
                '}';
    }
}
