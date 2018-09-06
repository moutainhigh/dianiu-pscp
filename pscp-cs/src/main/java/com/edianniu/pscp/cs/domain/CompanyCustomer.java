package com.edianniu.pscp.cs.domain;

import com.edianniu.pscp.sps.domain.BaseDo;
import com.edianniu.pscp.sps.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:08:44
 */
public class CompanyCustomer extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long memberId;
    //$column.comments
    private String mobile;
    //$column.comments
    private String cpName;
    //$column.comments
    private String cpContact;
    //$column.comments
    private String cpContactMobile;
    //$column.comments
    private String cpPhone;
    //$column.comments
    private String cpAddress;
    //$column.comments
    private Long companyId;
    // 0(邀请，等待客户同意)   1(已绑定，客户已同意)   -1(拒绝)
    private Integer status;
    //最近邀请时间
    private Date invitationTime;
    //最近邀请同意时间
    private Date invitationAgreeTime;
    //最近邀请拒绝时间
    private Date invitationRejectTime;

    private Integer count;
    private Date createTimeStart;
    private String createTimeStartConvert;

    private Date createTimeEnd;
    private String createTimeEndConvert;
    //private Long customerCompanyId;

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

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeStartConvert() {
        return createTimeStartConvert;
    }

    public void setCreateTimeStartConvert(String createTimeStartConvert) {
        if (!StringUtils.isBlank(createTimeStartConvert)) {
            Date start = DateUtils.parse(createTimeStartConvert);
            this.setCreateTimeStart(start);
        }
        this.createTimeStartConvert = createTimeStartConvert;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeEndConvert() {
        return createTimeEndConvert;
    }

    public void setCreateTimeEndConvert(String createTimeEndConvert) {
        if (!StringUtils.isBlank(createTimeEndConvert)) {
            Date end = DateUtils.parse(createTimeEndConvert);
            this.setCreateTimeEnd(end);
        }
        this.createTimeEndConvert = createTimeEndConvert;
    }

    /*
    public Long getCustomerCompanyId() {
        return customerCompanyId;
    }
    public void setCustomerCompanyId(Long customerCompanyId) {
        this.customerCompanyId = customerCompanyId;
    }
    */
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
