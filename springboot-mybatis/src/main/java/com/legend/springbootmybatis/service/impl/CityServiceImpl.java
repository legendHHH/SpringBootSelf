package com.legend.springbootmybatis.service.impl;

import com.legend.springbootmybatis.dao.CityDao;
import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 城市业务逻辑实现类
 *
 * Created by legend
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public City findCityByName(String cityName) {
        return cityDao.findByName(cityName);
    }

}