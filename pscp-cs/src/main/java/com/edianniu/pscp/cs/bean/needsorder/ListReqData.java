package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-09-22 16:58
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 登录用户ID.*/
    private Long uid;
    /* 需求名称.*/
    private String name;
    /* 需求联系人.*/
    private String contactPerson;
    /* 需求联系电话.*/
    private String contactNumber;
    private int offset;
    private Integer pageSize;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
