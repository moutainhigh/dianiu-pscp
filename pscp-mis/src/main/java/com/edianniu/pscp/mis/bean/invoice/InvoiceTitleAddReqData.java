package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class InvoiceTitleAddReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long uid;
    private String companyName;
    private String taxpayerId;
    private String contactNumber;
    private String bankCardNo;
    private String bankCardAddress;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardAddress() {
        return bankCardAddress;
    }

    public void setBankCardAddress(String bankCardAddress) {
        this.bankCardAddress = bankCardAddress;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
