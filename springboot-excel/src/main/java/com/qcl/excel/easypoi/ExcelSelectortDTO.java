package com.qcl.excel.easypoi;

import lombok.Data;

/**
 * 下拉框实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/30
 */
@Data
public class ExcelSelectortDTO {

    /**
     * 第几个sheet，从0开始
     */
    private int sheetIndex;

    /**
     * 下拉单元格行号，从0开始
     */
    private int firstRow;

    /**
     * 下拉单元格结束行号
     */
    private int lastRow;

    /**
     * 下拉单元格列号，从0开始
     */
    private int firstCol;

    /**
     * 下拉单元格列(结束)
     */
    private int lastCol;

    /**
     * 动态生成的下拉内容，easypoi使用的是字符数组
     */
    private String[] datas;
}
