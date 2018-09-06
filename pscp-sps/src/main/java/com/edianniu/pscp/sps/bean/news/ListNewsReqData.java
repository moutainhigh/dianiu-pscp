package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: ListNewsReqData
 * Author: tandingbo
 * CreateTime: 2017-08-11 09:58
 */
public class ListNewsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer type;
    private String name;
    private int offset;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
