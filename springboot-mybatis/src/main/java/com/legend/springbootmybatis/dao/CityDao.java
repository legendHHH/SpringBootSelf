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
}
