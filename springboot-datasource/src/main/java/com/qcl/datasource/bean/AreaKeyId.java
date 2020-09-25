package com.qcl.datasource.bean;

/**
 * 用户登陆,获取用户的地区,判断是028还是010根据这个获取对应的数据源进行操作对应的数据库
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
public enum AreaKeyId {
    //已经定好的状态标记不能更改，数据库有对应操作
    BEIJING("北京", "010"), CHENGDU("成都", "028");

    private String name;
    private String area;

    private AreaKeyId(String name, String area) {
        this.setName(name);
        this.setArea(area);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
