package com.legend.springbootmybatis.service;

import com.legend.springbootmybatis.domain.City;

import java.util.List;

/**
 * 城市业务逻辑接口类
 * <p>
 * Created by legend
 */
public interface CityService {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName
     * @return
     */
    City findCityByName(String cityName);

    /**
     * 根据城市名称，查询城市信息
     *
     * @param id
     * @return
     */
    City findCityById(Long id);

    /**
     * 更新数据
     *
     * @param city
     * @return
     */
    void update(City city);

    /**
     * 更新数据
     *
     * @param city
     * @return
     */
    City update2(City city);

    /**
     * 保存或者更新
     *
     * @param city
     * @return
     */
    void batchInsert(List<City> city);

    /**
     * 保存或者更新
     *
     * @param city
     * @return
     */
    int saveOrUpdate(City city);

    /**
     * 批量保存或者更新
     *
     * @param city
     * @return
     */
    int batchSaveOrUpdate(List<City> city);

    /**
     * 测试事务
     *
     * @param id
     * @return
     */
    City testSelect(long id);

    /**
     * 批量更新第一种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate1(List<City> cityList);

    /**
     * 批量更新第二种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate2(List<City> cityList);

    /**
     * 批量更新第三种方式
     *
     * @param cityList
     * @return
     */
    void batchUpdate3(List<City> cityList);
}
