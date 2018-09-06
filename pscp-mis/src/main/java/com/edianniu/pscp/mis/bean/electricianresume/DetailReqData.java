package com.edianniu.pscp.mis.bean.electricianresume;

import java.io.Serializable;

/**
 * ClassName: DetailReqData
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:25
 */
public class DetailReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 电工ID
     */
    private Long electricianId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }
}
