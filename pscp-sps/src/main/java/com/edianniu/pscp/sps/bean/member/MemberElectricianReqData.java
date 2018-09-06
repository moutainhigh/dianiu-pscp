package com.edianniu.pscp.sps.bean.member;

import java.io.Serializable;

/**
 * ClassName: MemberElectricianReqData
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:46
 */
public class MemberElectricianReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
