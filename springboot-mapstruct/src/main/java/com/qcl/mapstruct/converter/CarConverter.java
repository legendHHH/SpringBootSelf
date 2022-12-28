package com.qcl.mapstruct.converter;

import com.qcl.mapstruct.dto.CarDTO;
import com.qcl.mapstruct.entity.Car;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 实体类型转化接口使用 org.mapstruct.@Mapper 标识
 *
 *
 * 两个对象属性名相同，类型可能不同：可以直接写转换方法，不用写映射关系
 * @Mapper(componentModel="spring")  将Mapper注入到Spring IOC容器
 * @Mapper(imports={LocalDateTime.class})  使用LocalDateTime作为当前时间值注入到addTime属性中
 *
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/25
 */
@Mapper(componentModel = "spring",imports = {LocalDateTime.class})
public interface CarConverter {

    /**
     * 用来调用实例,实际开发中可使用注入spring 不写
     */
    CarConverter CAR_MAPPING = Mappers.getMapper(CarConverter.class);

    /**
     * 源类型 目标类型 成员变量相同类型 相同变量名 不用写 @Mapping 来映射
     *
     * @Mapping 注解用来声明成员属性的映射
     * source 代表转化的源这里就是Car
     * target 代表转化的目标这里是CarDTO
     *
     *
     * @param car
     * @return CarDTO
     */
    @Mapping(target = "type", source = "carType.type")
    @Mapping(target = "seatCount", source = "numberOfSeats")
    @Mapping(target = "price", numberFormat = "$#.00")
    //@Mapping(target = "createTime", expression = "java(LocalDateTime.now())")
    CarDTO carToCarDTO(Car car);

    /**
     * 字符串转list的Date
     * @param dateList
     * @return
     */
    @IterableMapping(dateFormat = "yyyy-MM-dd")
    List<String> stringListToDateList(List<Date> dateList);
}
