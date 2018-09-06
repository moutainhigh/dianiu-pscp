package com.edianniu.pscp.sps.bean.workorder.chieforder;

import java.io.Serializable;

/**
 * ClassName: InitDataReqData
 * Author: tandingbo
 * CreateTime: 2017-06-28 10:39
 */
public class InitDataReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
