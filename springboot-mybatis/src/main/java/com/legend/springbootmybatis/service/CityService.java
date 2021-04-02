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
}
