package com.example.demo.designpatterns.constructor;

/**
 * 创建构建类（将通过组装简单对象合并成复杂对象）
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class ComputerBuilder {

    private Computer computer = new Computer();

    /**
     * 构建组件
     *
     * @param display
     */
    public void installDisplay(String display) {
        computer.setDisplay(display);
    }

    public void installMainUnit(String mainUnit) {
        computer.setMainUnit(mainUnit);
    }

    public void installMouse(String mouse) {
        computer.setMouse(mouse);
    }

    public void installKeyBoard(String keyBoard) {
        computer.setKeyboard(keyBoard);
    }

    public Computer getComputer() {
        return computer;
    }
}
