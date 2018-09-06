package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.domain.CompanyCustomer;

/**
 * ClassName: DetailsResult
 * Author: tandingbo
 * CreateTime: 2017-08-18 11:33
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    private CompanyCustomer companyCustomer;

    public CompanyCustomer getCompanyCustomer() {
        return companyCustomer;
    }

    public void setCompanyCustomer(CompanyCustomer companyCustomer) {
        this.companyCustomer = companyCustomer;
    }
}
