package com.qcl.shiro.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
public class Role {

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 角色名称
     */
    private String rname;

    /**
     * 权限集合
     */
    private Set<Permission> permissions = new HashSet<>();

    /**
     * 用户集合
     */
    private Set<User> users = new HashSet<>();

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
