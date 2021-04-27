package com.legend.springbootmybatis.service.impl;

import com.legend.springbootmybatis.dao.CityDao;
import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public City findCityById(Long id) {
        return cityDao.findById(id);
    }

    @Override
    public void update(City city) {
        cityDao.update(city);
    }

    @Override
    public City update2(City city) {
        cityDao.update2(city);
        return cityDao.findById(city.getId());
    }

    @Override
    public void batchInsert(List<City> city) {
        cityDao.batchInsert(city);
    }

    @Override
    public int saveOrUpdate(City city) {
        return cityDao.saveOrUpdate(city);
    }


    @Override
    public int batchSaveOrUpdate(List<City> city) {
        return cityDao.batchSaveOrUpdate(city);
    }

    @Override
    public City testSelect(long id) {
        return cityDao.findById(id);
    }

    @Override
    public void batchUpdate1(List<City> cityList) {
        cityDao.batchUpdate1(cityList);
    }

    @Override
    public void batchUpdate2(List<City> cityList) {
        cityDao.batchUpdate2(cityList);
    }
}
