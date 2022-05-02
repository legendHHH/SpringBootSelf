package com.legend.springboot.repository;

import com.legend.springboot.entity.MoreData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 继承 JpaRepository来完成对数据库的操作
 * JpaRepository<MoreData, Integer>  第一个参数指定的是你要操作的对象,第二个参数是操作的对象中的主键类型
 */
public interface MoreDataRepository extends JpaRepository<MoreData, Integer> {
}
