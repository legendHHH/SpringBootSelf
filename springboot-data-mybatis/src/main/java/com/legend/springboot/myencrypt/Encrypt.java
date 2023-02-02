package com.legend.springboot.myencrypt;

/**
 * XXX
 *
 * @author qichunlin@grgbanking.com
 * @version 1.0
 * @description
 * @date 2022/10/19
 */
public class Encrypt {
    private String value;

    public Encrypt() {
    }

    public Encrypt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}