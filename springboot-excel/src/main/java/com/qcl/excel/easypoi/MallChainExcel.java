package com.qcl.excel.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模板实体类
 * http://doc.wupaas.com/docs/easypoi/easypoi-1c0u4mo8p4ro8
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/30
 */
@Data
@AllArgsConstructor
@Builder
public class MallChainExcel implements Serializable {

    @Excel(name = "所属POS", orderNum = "0", width = 15, replace = {"国大POS_gdpos", "内蒙古POS_nmgpos", "沈阳POS_sypos", "英克POS_sinpos"}, addressList = true)
    private String posStr = "";

    @Excel(name = "*门店编码", orderNum = "1", width = 15)
    private String chainCode;

    @Excel(name = "*门店名称", orderNum = "2", width = 15)
    private String chainName;

    @Excel(name = "地址", orderNum = "3", width = 15)
    private String address;

    @Excel(name = "店长姓名", orderNum = "4", width = 15)
    private String master;

    @Excel(name = "店长电话", orderNum = "5", width = 15)
    private String phone;

//    @Excel(name = "省", orderNum = "5")
//    private String province;
//
//    @Excel(name = "市", orderNum = "6")
//    private String city;
//
//    @Excel(name = "区", orderNum = "7")
//    private String area;

//    @Excel(name = "经度", orderNum = "11")
//    private String longitude;
//
//    @Excel(name = "维度", orderNum = "12")
//    private String latitude;

//    @Excel(name = "营业时间", orderNum = "13")
//    private String businessTime;
//
//    @Excel(name = "营业状态", orderNum = "14")
//    private String status;

//    @Excel(name = "公司ID", orderNum = "15")
//    private String companyId;

    public MallChainExcel() {
    }


}
