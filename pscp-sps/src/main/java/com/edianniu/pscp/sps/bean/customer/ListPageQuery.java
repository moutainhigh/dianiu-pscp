package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.commons.BaseQuery;

import java.util.Date;

/**
 * ClassName: ListPageQuery
 * Author: tandingbo
 * CreateTime: 2017-08-18 12:00
 */
public class ListPageQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long companyId;

    /* 搜索字段.*/
    private String mobile;
    private String cpName;
    private String cpContact;
    private Date createTimeStart;
    private Date createTimeEnd;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpContact() {
        return cpContact;
    }

    public void setCpContact(String cpContact) {
        this.cpContact = cpContact;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
