package com.legend.spring.dao;

import org.springframework.stereotype.Repository;

/**
 * 名字默认是类名的首字母小写bookDao
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
@Repository
public class BookDao {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
