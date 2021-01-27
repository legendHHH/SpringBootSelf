package com.qcl.excel.repository;

import com.qcl.excel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 继承 JpaRepository来完成对数据库的操作
 * JpaRepository<User, Integer>  第一个参数指定的是你要操作的对象,第二个参数是操作的对象中的主键类型
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/26
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
