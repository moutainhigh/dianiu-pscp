package com.edianniu.pscp.sps.bean.member;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: MemberCustomerResult
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:50
 */
public class MemberCustomerResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 用户登录名称.*/
    private String userName;
    /* 用户登录ID.*/
    private String userId;
    /* 角色（客户）.*/
    private String role;
    /* 认证状态(未认证，审核中，认证成功，认证失败).*/
    private String certifiedStatus;
    /* 公司名称.*/
    private String companyName;
    /* 公司地址.*/
    private String companyAddr;
    /* 单位联系电话.*/
    private String contactNumber;
    /* 单位联系人.*/
    private String contacts;
    /* 联系人电话.*/
    private String companyPhone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCertifiedStatus() {
        return certifiedStatus;
    }

    public void setCertifiedStatus(String certifiedStatus) {
        this.certifiedStatus = certifiedStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}
