package com.legend.spring.bean;

/**
 * 组件测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class Color {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
