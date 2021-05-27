package com.legend.springbootmybatis.controller;

import com.legend.springbootmybatis.domain.City;
import com.legend.springbootmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping(value = "/api/city6", method = RequestMethod.GET)
    public List<City> findSomeCity(City city) {
        city.setFlag(true);
        city.setIdList(Arrays.asList(3,4,5));
        return cityService.findSomeCity(city);
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
    public String method() {
        City city = new City();
        city.setId(5L);
        city.setCityName("889999998888");
        cityService.update(city);
        return cityService.testSelect(5L).toString();
    }

    /**
     * 测试更新后的数据返回
     *
     * @return
     */
    @GetMapping("/updateAfter")
    public String updateAfter() {
        City city = new City();
        city.setId(4L);
        city.setCityName("测试123456");
        city.setProvinceId(12L);
        return cityService.update2(city).toString();
    }

    /**
     * 批量更新第一种方式
     * http://localhost:8083/batchUpdate1
     *
     * @return
     */
    @GetMapping("/batchUpdate1")
    public String batchUpdate1() {
        System.out.println("hello");
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(4L);
        city.setProvinceId(999L);
        city.setCityName("南京西路");

        City city2 = new City();
        city2.setId(5L);
        city2.setCityName("南京东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate1(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    /**
     * 批量更新第二种方式
     * http://localhost:8083/batchUpdate2
     *
     * @return
     */
    @GetMapping("/batchUpdate2")
    public String batchUpdate2() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(4L);
        city.setProvinceId(999L);
        city.setCityName("南京1111西路");

        City city2 = new City();
        city2.setId(5L);
        city2.setCityName("南京22222东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate2(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    /**
     * 批量更新第二种方式
     * http://localhost:8083/batchUpdate3
     *
     * @return
     */
    @GetMapping("/batchUpdate3")
    public String batchUpdate3() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(44L);
        city.setProvinceId(999L);
        city.setCityName("南京1111西路");

        City city2 = new City();
        city2.setId(55L);
        city2.setCityName("南京22222东路");

        cityList.add(city);
        cityList.add(city2);

        cityService.batchUpdate3(cityList);
        return "更新了 " + 0 + " 条数据";
    }

    public static void main(String[] args) {
        City city = new City();
        System.out.println(city.isFlag());
        city.setId(-1L);

        List<Integer> in = new ArrayList<>();
        System.out.println(Long.parseLong(String.valueOf(city.getId())));
        System.out.println(in.toString());
    }
}
