package com.hkd.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by 1 on 2017-08-31.
 */
public class PageInfoApp<T>  {

    String  page;

    String pageSize;

    String pageTotal;

    private List<T>  item ;


    public PageInfoApp(PageInfo pageInfo) {
        this.page = pageInfo.getPageNum() +"";
        this.item = pageInfo.getList();
        this.pageSize = pageInfo.getPageSize()+"";
        this.pageTotal = pageInfo.getPages()+"";
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }
}
