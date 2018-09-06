package com.edianniu.pscp.search.support.needs;

import java.io.Serializable;

/**
 * ClassName: NeedsPageListReqData
 * Author: tandingbo
 * CreateTime: 2017-10-18 11:02
 */
public class NeedsPageListReqData implements Serializable {
    private static final long serialVersionUID = -101315817790932729L;

    private int offset;
    private Integer pageSize;

    /**
     * 搜索条件
     */
    private String searchText;

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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
