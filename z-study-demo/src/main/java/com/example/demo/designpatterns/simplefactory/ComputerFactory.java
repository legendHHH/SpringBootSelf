package com.example.demo.designpatterns.simplefactory;

/**
 * 工厂类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class ComputerFactory {

    public static Computer createComputer(String type) {
        Computer computer = null;
        switch (type) {
            case "lenovo":
                computer = new LenovoComputer();
                break;
            case "hp":
                computer = new HPComputer();
                break;
            default:
                break;
        }
        return computer;
    }
}
