package com.example.demo.reflection;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GdOrderGoodsVo
 * @date 2021/11/11 10:52
 * @Version 1.0
 */
@Data
public class GdOrderGoodsVo implements Serializable {

    private String id;//商品id
    private String supplierId;//国大商品id
    private String goodsCode;//商品69码
    private String name;//商品名称
    private String price;//商品原价（分）
    private String count;//购买数量
    private String spec;//药品规格
    private String factoryName;//生产商
    private String approvalNo;//批准文号
    private String productionBatchNo;//生产批次号
    private String goodsItemPrice;//细单核销金额(分)

    public GdOrderGoodsVo(String supplierId) {
        this.supplierId = supplierId;
    }
}
