package com.example.demo.designpatterns.constructor;

/**
 * 创建目标类（复杂对象）
 *
 * 将需要构建的目标类分成多个部件
 *
 * 使用构建者设计模式来生产Computer
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class Computer {

    /**
     * 显示器（简单对象）
     */
    private String display;

    /**
     * 主机（简单对象）
     */
    private String mainUnit;

    /**
     * 鼠标（简单对象）
     */
    private String mouse;

    /**
     * 键盘（简单对象）
     */
    private String keyboard;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMainUnit() {
        return mainUnit;
    }

    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "display='" + display + '\'' +
                ", mainUnit='" + mainUnit + '\'' +
                ", mouse='" + mouse + '\'' +
                ", keyboard='" + keyboard + '\'' +
                '}';
    }
}
