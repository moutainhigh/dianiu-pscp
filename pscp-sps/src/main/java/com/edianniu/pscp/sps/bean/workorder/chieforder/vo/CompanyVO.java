package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: CompanyVO
 * Author: tandingbo
 * CreateTime: 2017-05-16 16:40
 */
public class CompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 公司ID
     */
    private Long id;
    /**
     * 单位名称
     */
    private String name;
    /**
     * 单位联系人
     */
    private String contacts;
    /**
     * 单位联系电话
     */
    private String contactNumber;
    /**
     * 单位联系地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String toString() {
	        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
