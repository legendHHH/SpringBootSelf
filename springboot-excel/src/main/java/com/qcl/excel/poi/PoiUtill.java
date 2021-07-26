package com.qcl.excel.poi;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * POI测试工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/16
 */
public class PoiUtill {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\legend\\Desktop\\限时活动商品导入模板20210616094018.xlsx");
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = wb.getSheetAt(0);
        System.out.println(sheet);

        int rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row = sheet.getRow(2);
        System.out.println(row);
        row.getCell(0).setCellType(CellType.STRING);
        System.out.println(row.getCell(0).getStringCellValue());
    }
}
