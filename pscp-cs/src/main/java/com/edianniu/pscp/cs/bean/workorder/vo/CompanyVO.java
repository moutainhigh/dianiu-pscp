package com.edianniu.pscp.cs.bean.workorder.vo;

import java.io.Serializable;

/**
 * ClassName: CompanyVO
 * Author: tandingbo
 * CreateTime: 2017-08-08 11:47
 */
public class CompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* id.*/
    private Long id;
    /* 名称.*/
    private String name;
    /* 联系人.*/
    private String contacts;
    /* 联系电话.*/
    private String contactNumber;
    /* 联系地址.*/
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
