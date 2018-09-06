package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class InvoiceTitleDeleteReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    private Long id;

    private Long uid;

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
