package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 * 企业客户对象
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:08:44
 */
public class CompanyCustomer extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    
    private Long id;
    
    private Long memberId;
    
    private String mobile;
    //邀请状态
    private Integer status;
    
    private String cpName;
    
    private String cpContact;
    
    private String cpContactMobile;
    
    private String cpPhone;
    
    private String cpAddress;
    
    private Long companyId;
    //最近邀请时间
    private Date invitationTime;
    //最近同意时间
    private Date invitationAgreeTime;
    //最近拒绝时间
    private Date invitationRejectTime;

    
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
    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：${column.comments}
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCpName() {
        return cpName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCpContact(String cpContact) {
        this.cpContact = cpContact;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCpContact() {
        return cpContact;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCpContactMobile(String cpContactMobile) {
        this.cpContactMobile = cpContactMobile;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCpContactMobile() {
        return cpContactMobile;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCpPhone(String cpPhone) {
        this.cpPhone = cpPhone;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCpPhone() {
        return cpPhone;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCpAddress(String cpAddress) {
        this.cpAddress = cpAddress;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCpAddress() {
        return cpAddress;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
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
    
	
    
}
