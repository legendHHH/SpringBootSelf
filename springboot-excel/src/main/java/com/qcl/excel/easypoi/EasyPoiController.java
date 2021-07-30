package com.qcl.excel.easypoi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
