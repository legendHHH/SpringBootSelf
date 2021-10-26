package com.legend.elasticsearch.vo;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 返回前端门店信息
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/10/26
 */
public class SearchChainVo implements Serializable {

    /**
     * 门店id
     */
    private Long chainId;

    /**
     * 门店名字
     */
    private String chainName;

    /**
     * 门店编码
     */
    private String chainCode;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 门店经纬度
     */
    private Double chainLat;
    private Double chainLon;

    /**
     * 门店状态
     */
    private Long state;

    /**
     * 是否上线oms
     */
    private Long isOms;

    /**
     * 经纬度距离
     */
    private BigDecimal geoDistance;

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Double getChainLat() {
        return chainLat;
    }

    public void setChainLat(Double chainLat) {
        this.chainLat = chainLat;
    }

    public Double getChainLon() {
        return chainLon;
    }

    public void setChainLon(Double chainLon) {
        this.chainLon = chainLon;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getIsOms() {
        return isOms;
    }

    public void setIsOms(Long isOms) {
        this.isOms = isOms;
    }

    public BigDecimal getGeoDistance() {
        return geoDistance;
    }

    public void setGeoDistance(BigDecimal geoDistance) {
        this.geoDistance = geoDistance;
    }

    @Override
    public String toString() {
        return "SearchChainVo{" +
                "chainId=" + chainId +
                ", chainName='" + chainName + '\'' +
                ", chainCode='" + chainCode + '\'' +
                ", storeId=" + storeId +
                ", chainLat=" + chainLat +
                ", chainLon=" + chainLon +
                ", state=" + state +
                ", isOms=" + isOms +
                ", geoDistance=" + geoDistance +
                '}';
    }
}
