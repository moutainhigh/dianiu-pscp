package com.edianniu.pscp.sps.bean.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 * ClassName: CustmerInfo
 * Author: tandingbo
 * CreateTime: 2017-04-14 10:42
 */
public class CustomerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业主公司ID
     */
    private Long id;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 手机电话
     */
    private String mobile;
    
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
    /**
     * 业主公司座机
     */
    private String phone;
    
    private String address;
    
    private Integer status;
    
    //最近邀请时间
    private Date invitationTime;
    //最近同意时间
    private Date invitationAgreeTime;
    //最近拒绝时间
    private Date invitationRejectTime;
    
    /**
     * 添加时间
     */
    private Date createTime;
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public Date getInvitationTime() {
		return invitationTime;
	}

	public void setInvitationTime(Date invitationTime) {
		this.invitationTime = invitationTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getInvitationAgreeTime() {
		return invitationAgreeTime;
	}

	public void setInvitationAgreeTime(Date invitationAgreeTime) {
		this.invitationAgreeTime = invitationAgreeTime;
	}

	public Date getInvitationRejectTime() {
		return invitationRejectTime;
	}

	public void setInvitationRejectTime(Date invitationRejectTime) {
		this.invitationRejectTime = invitationRejectTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
