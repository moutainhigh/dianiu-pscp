package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class InvoiceApplyReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;

    private String payOrderId;
    private Long invoiceTitleId;


    public Long getInvoiceTitleId() {
        return invoiceTitleId;
    }

    public void setInvoiceTitleId(Long invoiceTitleId) {
        this.invoiceTitleId = invoiceTitleId;
    }


    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


}
