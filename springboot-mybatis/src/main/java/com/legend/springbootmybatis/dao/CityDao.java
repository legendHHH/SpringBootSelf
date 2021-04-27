package com.legend.springbootmybatis.dao;

import com.legend.springbootmybatis.domain.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市 DAO 接口类
 * <p>
 * Created by legend
 */
public interface CityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    City findByName(@Param("cityName") String cityName);

    /**
     * 根据主键id查询
     *
     * @param id
     * @return
     */
    City findById(@Param("id") Long id);

    /**
     * 更新数据
     *
     * @param city
     */
    void update(City city);

    /**
     * 更新数据
     *
     * @param city
     */
    void update2(City city);

    /**
     * 批量保存或更新
     *
     * @param cityList
     * @return
     */
    void batchInsert(@Param("list") List<City> cityList);

    /**
     * 保存或更新
     *
     * @param city
     * @return
     */
    int saveOrUpdate(City city);

    /**
     * 批量保存或更新
     *
     * @param cityList
     * @return
     */
    int batchSaveOrUpdate(@Param("list") List<City> cityList);

    /**
     * 批量更新第一种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate1(@Param("list") List<City> cityList);

    /**
     * 批量更新第二种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate2(@Param("list") List<City> cityList);

    /**
     * 批量更新第三种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate3(@Param("list") List<City> cityList);
}
