package com.example.demo.reflection;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GdOrderVo
 * @date 2021/11/11 10:43
 * @Version 1.0
 */
@Data
@ToString
public class GdOrderVo implements Serializable {

    private String id;//订单id
    @NotNull(message = "订单编号不为空")
    private String orderSn;//订单编号
    private String userId;//用户id
    private String mobile;//用户手机号
    private String consignee;//收件人
    private String consigneeAddress;//收件人地址
    private String consigneeProvince;//收件人省份
    private String consigneeCity;//收件人市
    private String consigneeArea;//收件人区
    private String consigneeLongitude;//收件地址经度
    private String consigneeLatitude;//收件地址纬度
    private String consigneePhone;//收件人手机号
    private String payStatus;//支付状态
    private String payTime;//支付时间
    private String posFee;//核销总金额（分）
    private String shopDiscountFee;//商户承担优惠
    private String platformDiscountFee;//平台承担优惠
    private String platformShipFee;//平台承担运费优惠
    private String shopShipFee;//商户承担的运费优惠
    private String packageFee;//包装费
    private String orderAmount;//订单实际支付金额
    private String TotalAmount;//订单总价
    private String status;//订单状态
    private String shopCode;//门店编号
    private String shopName;//门店名称
    private String prescription;//是否处方单
    private String prescriptionImg;//处方信息图片
    private String hospitalName;//开方医院
    private String doctorName;//开方医生
    private String patientName;//患者姓名
    private String prescriptionNo;//处方编号
    private String deliveryMethod;//配送方式
    private String platformKey;//平台标识
    private String seqNo;//订单序号
    private String vatNo;//纳税人识别号
    private String addressReglstered;//注册地址
    private String phoneReglstered;//注册电话
    private String depolsitBank;//开户银行
    private String bankAcount;//银行账户
    private String userAdress;//收票人地址
    private String userName;//收票人姓名
    private String userPhone;//收票人电话
    private String remark;//订单备注
    private String pickCode;//取货码
    private List<GdOrderGoodsVo> goodsList;  //商品信息


}
