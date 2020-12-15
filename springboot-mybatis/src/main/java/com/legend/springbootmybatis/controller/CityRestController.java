package com.legend.springbootmybatis.controller;

import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }


    @RequestMapping(value = "/api/city/saveOrUpdate", method = RequestMethod.GET)
    public int saveOrUpdate(City city) {
        return cityService.saveOrUpdate(city);
    }


    @RequestMapping(value = "/api/city/batchSaveOrUpdate", method = RequestMethod.GET)
    public int batchSaveOrUpdate() {
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            City city = new City();
            city.setDescription("北京路" + i + i);
            city.setCityName("北京路" + i);
            city.setProvinceId(i + 10000L);
            city.setId(i + 10L);
            cityList.add(city);
        }
        return cityService.batchSaveOrUpdate(cityList);
    }
}
