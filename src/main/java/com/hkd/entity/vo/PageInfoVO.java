package com.hkd.entity.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 传递分页相关的信息
 * Created by kun on 2017/5/30.
 */
public class PageInfoVO {
    private static final int DEFAULT_PAGE_SIZE = 10;
    @ApiModelProperty(value = "当前页数")
    private int pageNum;
    @ApiModelProperty(value = "每页多少条")
    private int pageSize;

    public PageInfoVO() {
    }

    public PageInfoVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
