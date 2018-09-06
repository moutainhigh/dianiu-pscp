package com.edianniu.pscp.cs.bean.needs;

import com.edianniu.pscp.cs.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: NeedsMarketListReqData
 * Author: tandingbo
 * CreateTime: 2017-09-21 10:38
 */
public class NeedsMarketListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 需求名称搜索字段
     */
    private String searchText;
    private Long uid;
    private int offset;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
