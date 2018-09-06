package com.edianniu.pscp.sps.bean.member;

import java.io.Serializable;

/**
 * ClassName: MemberCustomerReqData
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:50
 */
public class MemberCustomerReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
