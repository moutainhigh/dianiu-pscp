package com.edianniu.pscp.sps.bean.company;

import java.io.Serializable;

/**
 * 服务商信息
 * ClassName: CompanyInfo
 * Author: tandingbo
 * CreateTime: 2017-04-14 10:40
 */
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 派单公司ID
     */
    private Long id;
    /**
     * 派单公司名称
     */
    private String name;
    /**
     * 派单公司负责人
     */
    private String leader;
    /**
     * 派单公司联系电话
     */
    private String contactTel;
    
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
