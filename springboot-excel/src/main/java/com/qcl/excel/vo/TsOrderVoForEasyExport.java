package com.qcl.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class TsOrderVoForEasyExport implements Serializable {
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderSn;

    @ExcelProperty(value = "交易流水号", index = 1)
    private String paySn;

    @ExcelIgnore
    private int orderType;
    @ExcelProperty(value = "订单类型", index = 2)
    private String orderTypeDesc = "";
    @ExcelIgnore
    private String orderFrom;
    @ExcelProperty(value = "订单来源", index = 3)
    private String orderFromDesc = "";

    @ExcelProperty(value = "买家姓名", index = 4)
    private String buyerName;

    @ExcelProperty(value = "买家手机号", index = 5)
    private String buyerPhone;

    @ExcelProperty(value = "下单时间", index = 6)
    private Date addTime;

    @ExcelProperty(value = "支付时间", index = 7)
    private Date paymentTime;

    @ExcelProperty(value = "商品名称", index = 8)
    private String skuName;

    @ExcelProperty(value = "商品编码", index = 9)
    private String itemNo;

    @ExcelProperty(value = "sku编码", index = 10)
    private String skuCode;

    //规格
    @ExcelProperty(value = "规格", index = 11)
    private String spec;

    @ExcelProperty(value = "数量", index = 12)
    private int number;
    @ExcelIgnore
    private long unitPrice;
    @ExcelProperty(value = "单价", index = 13)
    private String unitPriceYuan = "";
    @ExcelIgnore
    private long goodsAmount;
    @ExcelProperty(value = "商品总金额", index = 14)
    private String goodsAmountYuan = "";
    @ExcelIgnore
    private long orderAmountOrigin;
    @ExcelProperty(value = "订单总金额", index = 15)
    private String orderAmountOriginYuan = "";
    @ExcelIgnore
    private long orderAmount;
    @ExcelProperty(value = "实付金额", index = 16)
    private String orderAmountYuan = "";
    @ExcelIgnore
    private long douliAmount;
    @ExcelProperty(value = "兜礼金额", index = 17)
    private String douliAmountYuan = "";
    @ExcelIgnore
    private long pukangAmount;
    @ExcelProperty(value = "普康金额", index = 18)
    private String pukangAmountYuan = "";
    @ExcelIgnore
    private int goodsKind;
    @ExcelIgnore
    private long pdAmount;

    @ExcelProperty(value = "预存款支付金额", index = 19)
    private String pdAmountYuan = "";
    @ExcelIgnore
    private long voucherPrice;
    @ExcelProperty(value = "优惠券金额", index = 20)
    private String voucherPriceYuan = "";
    @ExcelIgnore
    private long shippingFee;
    @ExcelProperty(value = "运费", index = 21)
    private String shippingFeeYuan = "";
    @ExcelIgnore
    private int orderState;
    @ExcelProperty(value = "订单状态", index = 22)
    private String orderStateDesc = "";
    @ExcelIgnore
    private int evaluationState;
    @ExcelIgnore
    private int deleteState;
    @ExcelIgnore
    private int refundState;
    @ExcelProperty(value = "退款状态", index = 23)
    private String refundStateDesc = "";
    @ExcelIgnore
    private long refundAmount;
    @ExcelProperty(value = "退款金额", index = 24)
    private String refundAmountYuan = "";

    @ExcelProperty(value = "运单编码", index = 25)
    private String shippingCode;
    @ExcelIgnore
    private Integer storeId;
    @ExcelProperty(value = "店铺编码", index = 26)
    private String storeCode;

    @ExcelProperty(value = "店铺名称", index = 27)
    private String storeName;
    @ExcelIgnore
    private int chainId;
    @ExcelProperty(value = "门店/大仓编码", index = 28)
    private String chainCode;

    @ExcelProperty(value = "门店/大仓名称", index = 29)
    private String chainName;

    @ExcelProperty(value = "渠道名称", index = 44)
    private String channelName;
    /**
     * 大仓名字
     */
    @ExcelIgnore
    private String storageName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @ExcelProperty(value = "处方请求编码", index = 30)
    private String reqNo;

    @ExcelProperty(value = "参与活动", index = 31)
    private String SkuActivityTypeDesc;
    @ExcelIgnore
    private String skuActivityType;

    @ExcelProperty(value = "收货人", index = 32)
    private String reciverName;
    @ExcelProperty(value = "收货电话", index = 33)
    private String reciverPhone;
    @ExcelProperty(value = "收货地址", index = 34)
    private String reciverInfo;

    @ExcelProperty(value = "会员卡", index = 35)
    private String cardNo;

    /**
     * 导出的Excel中添加两个字段“活动名称”和“佣金”，佣金的单位为员，保留两位小数。
     */
    @ExcelProperty(value = "活动名称", index = 36)
    private String shareName;
    @ExcelIgnore
    private Long commission;
    @ExcelProperty(value = "佣金", index = 37)
    private String commissionYuan;
    @ExcelIgnore
    private Long platformDiscount;
    @ExcelProperty(value = "平台优惠", index = 38)
    private String platformDiscountYuan;
    @ExcelIgnore
    private Long merchantDiscount;
    @ExcelProperty(value = "商户优惠", index = 39)
    private String merchantDiscountYuan;

    @ExcelProperty(value = "员工手机号", index = 40)
    private String employeePhone;


    @ExcelProperty(value = "分享者手机号", index = 41)
    private String sharePhone;

    @ExcelProperty(value = "分享者姓名", index = 42)
    private String shareUserName;
    @ExcelIgnore
    private Integer shareType;

    @ExcelProperty(value = "分享者类型", index = 43)
    private String shareTypeNmae;

    /**
     * 商品id
     *
     * @mbg.generated
     */
    @ExcelIgnore
    private Integer productId;

    public Long getPukangAmount() {
        return pukangAmount;
    }

    public void setPukangAmount(Long pukangAmount) {
        this.pukangAmount = pukangAmount;
    }


    public Long getOrderAmountOrigin() {
        return orderAmountOrigin;
    }

    public void setOrderAmountOrigin(Long orderAmountOrigin) {
        this.orderAmountOrigin = orderAmountOrigin;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getEmployeePhone() {
        if ("-1".equals(employeePhone)) {
            return "";
        }
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getReciverPhone() {
        return reciverPhone;
    }

    public void setReciverPhone(String reciverPhone) {
        this.reciverPhone = reciverPhone;
    }

    public String getReciverInfo() {
        return reciverInfo;
    }

    public void setReciverInfo(String reciverInfo) {
        this.reciverInfo = reciverInfo;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public Integer getGoodsKind() {
        return goodsKind;
    }

    public void setGoodsKind(Integer goodsKind) {
        this.goodsKind = goodsKind;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getPdAmount() {
        return pdAmount;
    }

    public void setPdAmount(Long pdAmount) {
        this.pdAmount = pdAmount;
    }

    public Long getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(Long voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(Integer evaluationState) {
        this.evaluationState = evaluationState;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setPlatformDiscount(Long platformDiscount) {
        this.platformDiscount = platformDiscount;
    }

    public Long getPlatformDiscount() {
        return platformDiscount;
    }

    public String getPlatformDiscountYuan() {
        if (platformDiscount != null) {
            return new BigDecimal(platformDiscount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
        } else {
            return null;
        }
    }

    public Long getMerchantDiscount() {
        return merchantDiscount;
    }

    public void setMerchantDiscount(Long merchantDiscount) {
        this.merchantDiscount = merchantDiscount;
    }

    public String getMerchantDiscountYuan() {
        if (merchantDiscount != null) {
            return new BigDecimal(merchantDiscount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
        } else {
            return null;
        }
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }

    public String getShareUserName() {
        return shareUserName;
    }

    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getShareTypeNmae() {
        if (shareType != null) {
            if (shareType == 1) {
                return "普通用户";
            }
            if (shareType == 2) {
                return "员工用户";
            }
        }
        return "";
    }

    public void setShareTypeNmae(String shareTypeNmae) {
        this.shareTypeNmae = shareTypeNmae;
    }

    public String getSkuActivityTypeDesc() {

        if ("share".equals(skuActivityType)) {
            return "分享佣金";
        } else if ("bundling".equals(skuActivityType)) {
            return "组合套餐";
        } else if ("xianshi".equals(skuActivityType)) {
            return "限时秒杀";
        } else if ("groupbuy".equals(skuActivityType)) {
            return "团购";
        } else {
            return "";
        }
    }

    public void setSkuActivityTypeDesc(String skuActivityTypeDesc) {
        SkuActivityTypeDesc = skuActivityTypeDesc;
    }

    public String getOrderTypeDesc() {
        if (this.orderType == 1) {
            return "快递";
        } else if (this.orderType == 2) {
            return "闪送";
        } else if (this.orderType == 3) {
            return "门店自提";
        } else if (this.orderType == 4) {
            return "处方快递";
        } else if (this.orderType == 5) {
            return "处方闪送";
        } else if (this.orderType == 6) {
            return "处方门店自提";
        } else if (this.orderType == 7) {
            return "积分快递";
        } else if (this.orderType == 8) {
            return "积分闪送";
        } else if (this.orderType == 9) {
            return "积分门店自提";
        } else if (this.orderType == 10) {
            return "普通门店同城配送";
        } else if (this.orderType == 11) {
            return "处方门店同城配送";
        } else if (this.orderType == 12) {
            return "积分同城配送";
        }
        return "";
    }

    public String getOrderStateDesc() {
        if (deleteState == 1) {
            return "已删除";
        }

        if (refundState == 1) {
            if (orderState == 80) {
                return "已退款(部分退)";
            }
            if (orderState == 70) {
                return "已收货(部分退)";
            }
            if (orderState == 60) {
                return "已发货(部分退)";
            }
            if (orderState == 50) {
                return "已验货(部分退)";
            }
            if (orderState == 40) {
                return "已打单(部分退)";
            }
            if (orderState == 30) {
                return "已确认(部分退)";
            }
            if (orderState == 20) {
                return "待确认(部分退)";
            }
        }
        if (refundState == 2) {
            return "已退款(全部)";
        }
        if (refundState == 10) {
            return "退款中";
        }
        if (evaluationState == 1) {
            return "已评价";
        }
        if (orderState == 0) {
            return "已取消";
        } else if (orderState == 10) {
            return "待付款";
        } else if (orderState == 20) {
            return "待确认";
        } else if (orderState == 30) {
            return "已确认";
        } else if (orderState == 40) {
            return "已打单";
        } else if (orderState == 50) {
            return "已验货";
        } else if (orderState == 60) {
            return "已发货";
        } else if (orderState == 70) {
            return "已收货";
        }
        return null;
    }

    public String getRefundStateDesc() {
        if (refundState == 0) {
            return "未退款";
        } else if (orderState == 1 || orderState == 2) {
            return "已退款";
        } else {
            return "";
        }
    }

    public String getGoodsAmountYuan() {
        return new BigDecimal(goodsAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getOrderAmountYuan() {
        return new BigDecimal(orderAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getPdAmountYuan() {
        return new BigDecimal(pdAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getRefundAmountYuan() {
        return new BigDecimal(refundAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getShippingFeeYuan() {
        return new BigDecimal(shippingFee).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getVoucherPriceYuan() {
        return new BigDecimal(voucherPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    public String getUnitPriceYuan() {
        return new BigDecimal(unitPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }

    /**
     * 将shareMoney的单位转换成“元”，保留两位小数。
     *
     * @return
     */
    public String getCommissionYuan() {
        if (commission != null) {
            return new BigDecimal(commission).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
        } else {
            return null;
        }
    }

    public String getOrderFromDesc() {
        if (orderFrom == null) {
            return "微信商城2.0";
        } else if (orderFrom == "") {
            return "微信商城2.0";
        } else if ("ELEME".equals(orderFrom)) {
            return "饿了么";
        } else if ("ALY".equals(orderFrom)) {
            return "阿里云药房";
        } else if ("PINGAN".equals(orderFrom)) {
            return "平安健康";
        } else if ("MEITUAN".equals(orderFrom)) {
            return "美团";
        } else if ("PINGANB2C".equals(orderFrom)) {
            return "平安B2C";
        } else if ("100002".equals(orderFrom)) {
            return "深圳云药房";
        } else if ("AREA".equals(orderFrom)) {
            return "其他区域代发";
        } else if ("100003".equals(orderFrom)) {
            return "河北外配处方";
        } else if ("100004".equals(orderFrom)) {
            return "南山医院外配处方";
        } else if ("GD2".equals(orderFrom)) {
            return "国大2.0";
        } else if ("WeHealth".equals(orderFrom)) {
            return "微健康";
        } else if ("Tencent".equals(orderFrom)) {
            return "腾讯外配";
        } else if ("WeMini".equals(orderFrom)) {
            return "小程序";
        } else if ("WeChat".equals(orderFrom)) {
            return "微信商城2.0";
        } else if ("UnionPay".equals(orderFrom)) {
            return "银联";
        } else if ("PINGAN_APPOINT".equals(orderFrom)) {
            return "福田平安预约";
        } else if ("GD_APPOINT".equals(orderFrom)) {
            return "国大预约";
        } else if ("MDY".equals(orderFrom)) {
            return "美德医";
        } else if ("MDY_APP".equals(orderFrom)) {
            return "美德医app";
        } else if ("DOOOLY".equals(orderFrom)) {
            return "兜礼";
        } else if ("PUKANG".equals(orderFrom)) {
            return "普康";
        } else {
            return "";
        }
    }
}