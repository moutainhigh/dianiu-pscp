package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.domain.CompanyCustomer;

import java.io.Serializable;

/**
 * ClassName: SaveReqData
 * Author: tandingbo
 * CreateTime: 2017-08-18 11:03
 */
public class SaveReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private CompanyCustomer companyCustomer;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public CompanyCustomer getCompanyCustomer() {
        return companyCustomer;
    }

    public void setCompanyCustomer(CompanyCustomer companyCustomer) {
        this.companyCustomer = companyCustomer;
    }
}
