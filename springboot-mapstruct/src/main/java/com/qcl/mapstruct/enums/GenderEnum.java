package com.qcl.mapstruct.enums;

/**
 * 性别枚举类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/28
 */
public enum GenderEnum {
    Male("1", "男"),
    Female("0", "女");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    GenderEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}