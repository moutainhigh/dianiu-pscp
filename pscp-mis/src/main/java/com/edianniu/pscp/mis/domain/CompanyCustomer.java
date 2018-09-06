package com.edianniu.pscp.mis.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 企业客户信息
 * ClassName: CompanyCustomer
 * Author: tandingbo
 * CreateTime: 2017-04-16 14:57
 */
public class CompanyCustomer extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 业主单位
     */
    private String cpName;
    /**
     * 业主单位负责人
     */
    private String cpContact;
    /**
     * 联系人手机号码
     */
    private String cpContactMobile;
    /**
     * 业主单位电话号码
     */
    private String cpPhone;
    /**
     * 业主单位地址
     */
    private String cpAddress;
    /**
     * 0(邀请，等待客户同意)
     * 1(已绑定，客户已同意)
	 * -1(拒绝)
     */
    private Integer status;
    private Date invitationTime;//最近邀请时间
    private Date invitationAgreeTime;//最近邀请同意时间
    private Date invitationRejectTime;//最近邀请拒绝时间
    /**
     * 所属企业ID
     */
    private Long companyId;

    /**
     * 拼接联系方式
     *
     * @return
     */
    public String getContactTel() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(getMobile())) {
            sb.append(getMobile());
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            sb.append("/");
        }
        if (StringUtils.isNotBlank(getCpPhone())) {
            sb.append(getMobile());
        }
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCpContactMobile() {
        return cpContactMobile;
    }

    public void setCpContactMobile(String cpContactMobile) {
        this.cpContactMobile = cpContactMobile;
    }

    public String getCpPhone() {
        return cpPhone;
    }

    public void setCpPhone(String cpPhone) {
        this.cpPhone = cpPhone;
    }

    public String getCpAddress() {
        return cpAddress;
    }

    public void setCpAddress(String cpAddress) {
        this.cpAddress = cpAddress;
    }

    public Integer getStatus() {
		return status;
	}

	public Date getInvitationTime() {
		return invitationTime;
	}

	public Date getInvitationAgreeTime() {
		return invitationAgreeTime;
	}

	public Date getInvitationRejectTime() {
		return invitationRejectTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setInvitationTime(Date invitationTime) {
		this.invitationTime = invitationTime;
	}

	public void setInvitationAgreeTime(Date invitationAgreeTime) {
		this.invitationAgreeTime = invitationAgreeTime;
	}

	public void setInvitationRejectTime(Date invitationRejectTime) {
		this.invitationRejectTime = invitationRejectTime;
	}

	public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
