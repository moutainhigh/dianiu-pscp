package com.edianniu.pscp.mis.bean.company;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoujianjian
 * @date 2018年2月4日 下午7:48:42
 */
public class CompanyCustomerInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    /**
     * 客户会员ID
     */
    private Long memberId;
    /**
     * 客户手机号码
     */
    private String mobile;
    // 服务商ID
    private Long companyId;
    /**
     * 服务商单位
     */
    private String cpName;
    /**
     * 服务商负责人
     */
    private String cpContact;
    /**
     * 服务商联系人手机号码
     */
    private String cpContactMobile;
    /**
     * 服务商电话号码
     */
    private String cpPhone;
    /**
     * 服务商单位地址
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInvitationTime() {
		return invitationTime;
	}

	public void setInvitationTime(Date invitationTime) {
		this.invitationTime = invitationTime;
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

	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
