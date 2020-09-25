package com.qcl.datasource.bean;

/**
 * 地区实体
 * INSERT INTO `test`.`hjmall_area`(`area_code`, `area_parent_code`, `area_name`, `area_simple_name`, `area_level`, `area_sort`) VALUES ('410423', '410400', '鲁山县', '鲁山', '3', '1933');
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
public class Area {

    private String areaCode;
    private String areaParentCode;
    private String areaName;
    private String areaSimpleName;
    private String areaLevel;
    private String areaSort;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(String areaParentCode) {
        this.areaParentCode = areaParentCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSimpleName() {
        return areaSimpleName;
    }

    public void setAreaSimpleName(String areaSimpleName) {
        this.areaSimpleName = areaSimpleName;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(String areaSort) {
        this.areaSort = areaSort;
    }
}
