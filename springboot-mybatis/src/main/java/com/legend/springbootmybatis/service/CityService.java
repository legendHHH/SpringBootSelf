package com.legend.springbootmybatis.service;

import com.legend.springbootmybatis.domain.City;

/**
 * 城市业务逻辑接口类
 *
 * Created by legend
 */
public interface CityService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    City findCityByName(String cityName);
}
