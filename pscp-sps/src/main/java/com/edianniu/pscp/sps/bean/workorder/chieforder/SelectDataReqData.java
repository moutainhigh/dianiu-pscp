package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: SelectDataReqData
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:17
 */
public class SelectDataReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    private int offset;

    private Long uid;
    /**
     * 工单名称
     */
    private String name;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
