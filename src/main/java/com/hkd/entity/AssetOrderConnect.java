package com.hkd.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

@Table(name = "asset_order_connect")
public class AssetOrderConnect {
    @ApiModelProperty(value = "总订单ID",notes = "pool的ID")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "分期订单ID")
    private Integer assetOrderId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssetOrderId() {
        return assetOrderId;
    }

    public void setAssetOrderId(Integer assetOrderId) {
        this.assetOrderId = assetOrderId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}