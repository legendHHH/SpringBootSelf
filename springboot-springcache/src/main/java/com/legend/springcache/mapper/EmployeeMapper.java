package com.legend.springcache.mapper;

import com.legend.springcache.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 员工mapper
 *   注解CacheConfig(cacheNames = "cloud_employee")  cacheNames来源于 ehcache.xml 的cache节点中的name
 *   注解Cacheable指定那个方法数据需要被缓存
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@Mapper
@CacheConfig(cacheNames = "cloud_employee")
public interface EmployeeMapper {

    /**
     * 更新员工
     *
     * @param employee
     * @return
     */
    public Employee updateEmp(Employee employee);

    /**
     * 指定哪个方法处理缓存
     *
     * @return
     */
    @Cacheable
    public List<Employee> selectAll();

    public void insertEmp(Employee employee);
}
