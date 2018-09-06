package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class ConfirmInvoiceInfoReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    private String orderId;
    private String modifiedUser;
    private Long printUid;
    private Long printCompanyId;

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Long getPrintUid() {
        return printUid;
    }

    public void setPrintUid(Long printUid) {
        this.printUid = printUid;
    }

    public Long getPrintCompanyId() {
        return printCompanyId;
    }

    public void setPrintCompanyId(Long printCompanyId) {
        this.printCompanyId = printCompanyId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
