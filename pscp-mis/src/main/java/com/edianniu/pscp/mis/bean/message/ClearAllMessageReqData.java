package com.edianniu.pscp.mis.bean.message;

import java.io.Serializable;

/**
 * ClassName: ClearAllMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:34
 */
public class ClearAllMessageReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
