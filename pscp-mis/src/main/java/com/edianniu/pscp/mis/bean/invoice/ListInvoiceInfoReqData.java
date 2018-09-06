package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class ListInvoiceInfoReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 页码
     */
    private Integer offset;
    /**
     * 每页条数
     */
    private Integer pageSize;

    private Integer status;

    /**
     * 开票公司
     */
    private String payerCompanyName;

    /**
     * 支付日期
     */
    private String payDate;

    public String getPayerCompanyName() {
        return payerCompanyName;
    }

    public void setPayerCompanyName(String payerCompanyName) {
        this.payerCompanyName = payerCompanyName;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
