package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-09-12 16:51
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long id;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
