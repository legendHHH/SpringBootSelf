package com.qcl.excel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 导出数据对应的实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/26
 */
@Data
public class UserExcelVo2 extends BaseRowModel {

    @ExcelProperty(value = "序号", index = 0)
    private Integer id;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "地址", index = 2)
    private String address;

    @ExcelProperty(value = "年龄", index = 3)
    private String age;

    /**
     * 格式化日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "生日", index = 4)
    private Date birth;

    @ExcelProperty(value = "性别", index = 5)
    private String sex;

    @ExcelProperty(value = "是否显示", index = 6)
    private String isShow;

    @ExcelProperty(value = "图片", index = 7)
    private String image;

    @ExcelProperty(value = "备注信息", index = 8)
    private String remark;
}