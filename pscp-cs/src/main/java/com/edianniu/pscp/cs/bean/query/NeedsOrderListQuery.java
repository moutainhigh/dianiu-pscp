package com.edianniu.pscp.cs.bean.query;

import com.edianniu.pscp.cs.commons.BaseQuery;

/**
 * ClassName: NeedsOrderListQuery
 * Author: tandingbo
 * CreateTime: 2017-09-25 10:10
 */
public class NeedsOrderListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long companyId;
    private String name;
    private String contactPerson;
    private String contactNumber;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
    
}
