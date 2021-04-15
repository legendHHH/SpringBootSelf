package com.legend.springbootmybatis.controller;

import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    @RequestMapping(value = "/api/city/batchInsert", method = RequestMethod.GET)
    public int batchInsert() {
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            City city = new City();
            city.setDescription("北京路" + i + i);
            city.setCityName("北京路" + i);
            city.setProvinceId(i + 10000L);
            city.setId(i + 10L);
            cityList.add(city);
        }
        cityService.batchInsert(cityList);
        System.out.println(cityList.stream().map(City::getId).collect(Collectors.toList()));
        return 1;
    }


    @GetMapping("/testSelect")
    public String method(){
        City city = new City();
        city.setId(5L);
        city.setCityName("889999998888");
        cityService.update(city);
        return cityService.testSelect(5L).toString();
    }
}
