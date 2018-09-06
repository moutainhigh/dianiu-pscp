package com.edianniu.pscp.mis.domain;

import java.util.Date;

public class MemberInvoiceInfo extends BaseDo {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderId;
    private String payOrderIds;
    private String titleName;
    private String titleTaxpayerNo;
    private String titleContactNumber;
    private String titleBankCardNo;
    private String titleBankCardAddress;
    private Date applyTime;
    private Date printTime;
    private Integer status;
    private Long applyUid;
    private Long printUid;
    private Long printCompanyId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayOrderIds() {
        return payOrderIds;
    }

    public void setPayOrderIds(String payOrderIds) {
        this.payOrderIds = payOrderIds;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleTaxpayerNo() {
        return titleTaxpayerNo;
    }

    public void setTitleTaxpayerNo(String titleTaxpayerNo) {
        this.titleTaxpayerNo = titleTaxpayerNo;
    }

    public String getTitleContactNumber() {
        return titleContactNumber;
    }

    public void setTitleContactNumber(String titleContactNumber) {
        this.titleContactNumber = titleContactNumber;
    }

    public String getTitleBankCardNo() {
        return titleBankCardNo;
    }

    public void setTitleBankCardNo(String titleBankCardNo) {
        this.titleBankCardNo = titleBankCardNo;
    }

    public String getTitleBankCardAddress() {
        return titleBankCardAddress;
    }

    public void setTitleBankCardAddress(String titleBankCardAddress) {
        this.titleBankCardAddress = titleBankCardAddress;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public Long getApplyUid() {
        return applyUid;
    }

    public void setApplyUid(Long applyUid) {
        this.applyUid = applyUid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
