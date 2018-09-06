package com.edianniu.pscp.sps.bean.socialworkorder.resume;

import java.io.Serializable;

/**
 * ClassName: ResumeReqData
 * Author: tandingbo
 * CreateTime: 2017-05-24 14:47
 */
public class ResumeReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long electricianId;

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }
}
