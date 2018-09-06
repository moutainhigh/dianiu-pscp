package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: ListPageReqData
 * Author: tandingbo
 * CreateTime: 2017-08-18 11:40
 */
public class ListPageReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int offset;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    private Long uid;

    /* 搜索字段.*/
    private String mobile;
    private String cpName;
    private String cpContact;
    private String createTimeStartConvert;
    private String createTimeEndConvert;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public String getCreateTimeStartConvert() {
        return createTimeStartConvert;
    }

    public void setCreateTimeStartConvert(String createTimeStartConvert) {
        this.createTimeStartConvert = createTimeStartConvert;
    }

    public String getCreateTimeEndConvert() {
        return createTimeEndConvert;
    }

    public void setCreateTimeEndConvert(String createTimeEndConvert) {
        this.createTimeEndConvert = createTimeEndConvert;
    }
}
