package com.example.demo.designpatterns.constructor;

/**
 * 构建者设计模式测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class ConstructorTest {

    public static void main(String[] args) {
        ComputerBuilder builder = new ComputerBuilder();
        builder.installDisplay("显示器");
        builder.installKeyBoard("键盘");
        builder.installMainUnit("主机");
        builder.installMouse("鼠标");

        Computer computer = builder.getComputer();
        System.out.println(computer.toString());
    }
}
