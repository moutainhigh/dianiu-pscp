package com.edianniu.pscp.cs.bean.workorder;

import com.edianniu.pscp.cs.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-08-07 16:55
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private String status;
    private String name;
    private int offset;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
