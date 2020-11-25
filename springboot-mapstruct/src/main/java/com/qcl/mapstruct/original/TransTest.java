package com.qcl.mapstruct.original;

import com.qcl.mapstruct.dto.CarDTO;
import com.qcl.mapstruct.entity.Car;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/25
 */
public class TransTest {

    /**
     * 从数据库查询Car 然后转化为CarDTO
     *
     * @param car
     * @return
     */
    public CarDTO carToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setMake(car.getMake());
        carDTO.setSeatCount(car.getNumberOfSeats());
        carDTO.setType(car.getCarType().getType());
        return carDTO;
    }
}
