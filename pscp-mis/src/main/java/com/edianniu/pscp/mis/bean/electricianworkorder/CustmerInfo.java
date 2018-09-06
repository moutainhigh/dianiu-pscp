package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * 客户信息
 * ClassName: CustmerInfo
 * Author: tandingbo
 * CreateTime: 2017-04-14 10:42
 */
public class CustmerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业主公司ID
     */
    private Long id;
    /**
     * 业主公司名称
     */
    private String name;
    /**
     * 业主公司负责人
     */
    private String leader;
    /**
     * 业主公司联系电话
     */
    private String contactTel;

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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}
