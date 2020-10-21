package com.example.demo.optional;

/**
 * 学校实体
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/21
 */
public class School {

    private String schName;

    private String address;

    public School(String schName, String address) {
        this.schName = schName;
        this.address = address;
    }

    public School() {
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "School{" +
                "schName='" + schName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}