package com.qcl.excel.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/30
 */
@Slf4j
@Controller
public class EasyPoiController {

    /**
     * 下载模板
     * http://localhost:8089/downloadExcel
     *
     * @param response
     */
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
    public void downloadExcel(HttpServletResponse response) {
        try {
            List<MallChainExcel> mallChainImportExcels = new ArrayList<>();
            MallChainExcel mallChainImportExcel = new MallChainExcel();
            mallChainImportExcels.add(mallChainImportExcel);

            //不能给空对象不然读取的都是null
            /*List<MallChainExcel> mallChainImportExcels = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                MallChainExcel mallChainImportExcel = new MallChainExcel();
                mallChainImportExcels.add(mallChainImportExcel);
            }*/
            //导出操作
            //ExpAndImpUtil.exportExcel(mallChainImportExcels, "门店模板", "门店信息", MallChainExcel.class, "门店模板.xls", response);

            /*ExcelSelectortDTO excelSelectortDTO = new ExcelSelectortDTO();
            excelSelectortDTO.setFirstRow(2);
            excelSelectortDTO.setLastRow(1000);
            excelSelectortDTO.setFirstCol(0);
            excelSelectortDTO.setLastCol(0);
            excelSelectortDTO.setDatas(new String[]{"gdpos", "nmgpos"});
            ExpAndImpUtil.omsWithSelectorExportExcel(mallChainImportExcels, Arrays.asList(excelSelectortDTO), "门店模板", "门店信息", MallChainExcel.class, "门店模板.xls", response);*/

            //第二种方式
            ExpAndImpUtil.exportExcel(mallChainImportExcels, "门店模板", "门店信息", MallChainExcel.class, "门店模板.xls", response);

        } catch (Exception e1) {
            log.error(e1.toString());
        }
    }

    /**
     * 下载大数据量文件
     * http://localhost:8089/downloadExcel2
     *
     * @param response
     */
    @RequestMapping(value = "/downloadExcel2", method = RequestMethod.GET)
    public void downloadExcel2(HttpServletResponse response) {
        ExportParams params = new ExportParams("大数据测试", "测试");
        long startTime = System.currentTimeMillis();
        /**
         * params:（表格标题属性）筛选条件，sheet值
         * MsgClient：表格的实体类
         */
        Workbook workbook = ExcelExportUtil.exportBigExcel(params, MallChainExcel.class, new IExcelExportServer() {
            /**
             * obj 就是下面的10，限制条件
             * page 是页数，他是在分页进行文件转换，page每次+1
             */
            @Override
            public List<Object> selectListForExcelExport(Object obj, int page) {
                //page从1开始,每次加一,当等于obj的值时返回空,代码结束；
                if (((int) obj) == page) {
                    return null;
                }

                System.out.println("循环次数："  + page);
                //不是空时：一直循环运行selectListForExcelExport。每次返回1万条数据。
                List<Object> list = new ArrayList<Object>();
                for (int i = 0; i < 100000; i++) {
                    MallChainExcel client = new MallChainExcel();
                    client.setChainName("325252"+ "hhh  " + i);
                    client.setAddress("1111");
                    list.add(client);

                }
                return list;
            }
        }, 2);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时："+ (endTime - startTime));
        //String fileName, HttpServletResponse response, Workbook workbook
        ExpAndImpUtil.downLoadExcel("大数据ceshi.xlsx", response, workbook);

    }

    /**
     * list<bean> 转list<Object>
     * @param list
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> convert(List<?> list) {
        return (List<T>)list;
    }

    /**
     * 导入模板数据
     * http://localhost:8089/chainImport
     *
     * @param file
     */
    @RequestMapping(value = "/chainImport", method = RequestMethod.POST)
    @ResponseBody
    public Object importProductWaringExcel(@RequestParam MultipartFile file) {
        List<MallChainExcel> chainList = ExpAndImpUtil.importExcel(file, 1, 1, MallChainExcel.class);
        System.out.println("xxxx：" + chainList.toString());
        return chainList;
    }
}
