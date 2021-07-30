package com.qcl.excel.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/30
 */
public class ExpAndImpUtil {
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    /*public static void exportBigExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultBigExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }*/

    /*private static void defaultBigExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }*/

    /**
     * 手动添加下拉框
     *
     * @param list
     * @param selectors
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void omsWithSelectorExportExcel(List<?> list, List<ExcelSelectortDTO> selectors, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultBigExport(list, pojoClass, selectors, fileName, response, new ExportParams(title, sheetName));
    }

    private static void defaultBigExport(List<?> list, Class<?> pojoClass, List<ExcelSelectortDTO> selectors, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        //生成下拉框数据
        workbook = generateSelectors(workbook, selectors);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static Workbook generateSelectors(Workbook workbook, List<ExcelSelectortDTO> dataList){
        if (CollectionUtils.isEmpty(dataList)) {
            return workbook;
        }
        Sheet sheet;
        CellRangeAddressList cellRangeAddressList;
        DVConstraint dvConstraint;
        HSSFDataValidation dataValidation;
        for (ExcelSelectortDTO data : dataList) {
            sheet = workbook.getSheetAt(data.getSheetIndex());
            // 只对(x,x)单元格有效
            cellRangeAddressList = new CellRangeAddressList(data.getFirstRow(), data.getLastRow(), data.getFirstCol(), data.getLastCol());
            // 生成下拉框内容
            dvConstraint = DVConstraint.createExplicitListConstraint(data.getDatas());
            dataValidation = new HSSFDataValidation(cellRangeAddressList, dvConstraint);
            // 对sheet页生效
            sheet.addValidationData(dataValidation);
        }
        return workbook;
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public static void selectList(Workbook workbook,int firstCol,int lastCol,String[] strings ){

        Sheet sheet = workbook.getSheetAt(0);
        //  生成下拉列表
        //  只对(x，x)单元格有效
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(2, 65535, firstCol, lastCol);
        //  生成下拉框内容
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(strings);
        HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList, dvConstraint);
        //  对sheet页生效
        sheet.addValidationData(dataValidation);
    }

    public static void downLoadExcel(String fileName, Workbook workbook, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        OutputStream output = null;
        BufferedOutputStream bufferedOutPut = null;
        try {
            // 重置响应对象
            response.reset();
            // 当前日期，用于导出文件名称
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateStr = fileName + "-" + sdf.format(new Date());


            String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
            // 指定下载的文件名--设置响应头
            if (UserAgent.indexOf("firefox") >= 0) {

                response.setHeader("content-disposition", "attachment;filename=\"" + new String(dateStr.getBytes("UTF-8"), "iso-8859-1") +".xls\"");

            }else {
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(dateStr, "UTF-8")+".xls");
            }


            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            // 编码
            response.setCharacterEncoding("UTF-8");
            output = response.getOutputStream();
            bufferedOutPut = new BufferedOutputStream(output);

            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
        } catch (Exception e) {

        } finally {
            if (bufferedOutPut != null) {
                bufferedOutPut.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }
}
