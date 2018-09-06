package com.edianniu.pscp.mis.bean.invoice;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class InvoiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderId;
    private Integer status;
    private String companyName;
    private String amount;
    private String payTime;
    private String applyInvoiceTime;
    private String confirmInvoiceTime;
    private String taxpayerId;
    private String contactNumber;
    private Long printCompanyUid;  //客户所属公司uid
    private String payerName;
    private List<InvoiceInfoPay> pays;
    private Long applyUid;   //申请发票租客的uid

    public Long getApplyUid() {
		return applyUid;
	}

	public void setApplyUid(Long applyUid) {
		this.applyUid = applyUid;
	}

	public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public List<InvoiceInfoPay> getPays() {
        return pays;
    }

    public void setPays(List<InvoiceInfoPay> pays) {
        this.pays = pays;
    }

    public Long getPrintCompanyUid() {
        return printCompanyUid;
    }

    public void setPrintCompanyUid(Long printCompanyUid) {
        this.printCompanyUid = printCompanyUid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }


    public String getApplyInvoiceTime() {
        return applyInvoiceTime;
    }

    public void setApplyInvoiceTime(String applyInvoiceTime) {
        this.applyInvoiceTime = applyInvoiceTime;
    }

    public String getConfirmInvoiceTime() {
        return confirmInvoiceTime;
    }

    public void setConfirmInvoiceTime(String confirmInvoiceTime) {
        this.confirmInvoiceTime = confirmInvoiceTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
