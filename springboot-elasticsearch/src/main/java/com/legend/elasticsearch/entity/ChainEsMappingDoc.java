package com.legend.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;
import java.util.Date;

/**
 * ES搜索引擎门店信息Mapping
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/10/20
 */
@Document(indexName = "geo", type = "mall_chain_type", shards = 1, replicas = 0)
public class ChainEsMappingDoc implements Serializable {

    @Id
    private Long id;

    /**
     * 门店id
     */
    @Field(type = FieldType.Long)
    private Long chainId;

    /**
     * 门店名字
     */
    @Field(type = FieldType.Text)
    private String chainName;

    /**
     * 门店编码
     */
    @Field(type = FieldType.Text)
    private String chainCode;

    /**
     * 店铺id
     */
    @Field(type = FieldType.Long)
    private Long storeId;

    /**
     * 门店经纬度
     */
    @Field(type = FieldType.Double)
    private Double chainLat;

    @Field(type = FieldType.Double)
    private Double chainLon;

    /**
     * es的数据类型计算经纬度必须
     */
    @GeoPointField
    private GeoPoint location;

    /**
     * 门店状态
     */
    @Field(type = FieldType.Long)
    private Long state;

    /**
     * 是否上线oms
     */
    @Field(type = FieldType.Long)
    private Long isOms;

    @Field(type = FieldType.Date)
    private Date creationDate;

    @Field(type = FieldType.Date)
    private Date lastUpdateDate;

    /**
     * 创建时间
     */
    private String creationDateStr;

    /**
     * 最后更新时间
     */
    private String lastUpdateDateStr;

    @Field(type = FieldType.Text)
    private String dateStr;

    @Field(type = FieldType.Boolean)
    private boolean syncFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public String getLastUpdateDateStr() {
        return lastUpdateDateStr;
    }

    public void setLastUpdateDateStr(String lastUpdateDateStr) {
        this.lastUpdateDateStr = lastUpdateDateStr;
    }

    public boolean isSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(boolean syncFlag) {
        this.syncFlag = syncFlag;
    }

    public ChainEsMappingDoc() {
    }

    public ChainEsMappingDoc(Long id, Long chainId, String chainName, String chainCode, Long storeId, Double chainLat, Double chainLon, GeoPoint location, Long state, Long isOms) {
        this.id = id;
        this.chainId = chainId;
        this.chainName = chainName;
        this.chainCode = chainCode;
        this.storeId = storeId;
        this.chainLat = chainLat;
        this.chainLon = chainLon;
        this.location = location;
        this.state = state;
        this.isOms = isOms;
    }

    @Override
    public String toString() {
        return "ChainEsMappingDoc{" +
                "id=" + id +
                ", chainId=" + chainId +
                ", chainName='" + chainName + '\'' +
                ", chainCode='" + chainCode + '\'' +
                ", storeId=" + storeId +
                ", chainLat=" + chainLat +
                ", chainLon=" + chainLon +
                ", location=" + location +
                ", state=" + state +
                ", isOms=" + isOms +
                '}';
    }
}
