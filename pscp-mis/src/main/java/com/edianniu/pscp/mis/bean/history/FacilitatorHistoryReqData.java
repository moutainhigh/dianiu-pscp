package com.edianniu.pscp.mis.bean.history;

import java.io.Serializable;

/**
 * ClassName: FacilitatorHistoryReqData
 * Author: tandingbo
 * CreateTime: 2017-10-30 10:57
 */
public class FacilitatorHistoryReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Integer offset;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
