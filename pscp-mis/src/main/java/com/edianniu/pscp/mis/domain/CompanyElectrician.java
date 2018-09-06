package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 企业电工邀请信息表
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 下午4:32:45 
 * @version V1.0
 */
public class CompanyElectrician extends BaseDo {
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
     * 电工名字
     */
    private String name;
    /**
     * 0(邀请，等待电工同意)
     * 1(已绑定，电工已同意)
     * 2(申请解绑，企业)
     * 3(申请解绑，电工)
     * -1(拒绝)
     * -2(已解绑)
     * 
     */
    private Integer status;
    private Date invitationTime;//最近邀请时间
    private Date invitationAgreeTime;//最近邀请同意时间
    private Date invitationRejectTime;//最近邀请拒绝时间
    private Long applyUnbundUser;//最近申请解绑者
    private Date applyUnbundTime;//最近申请解绑时间
    private Date applyUnbundAgreeTime;//最近解绑同意时间
    private Date applyUnbundRejectTime;//最近解绑拒绝时间
    /**
     * 企业ID
     */
    private Long companyId;

   

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

	public String getName() {
		return name;
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

	public Long getApplyUnbundUser() {
		return applyUnbundUser;
	}

	public Date getApplyUnbundTime() {
		return applyUnbundTime;
	}

	public Date getApplyUnbundAgreeTime() {
		return applyUnbundAgreeTime;
	}

	public Date getApplyUnbundRejectTime() {
		return applyUnbundRejectTime;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setApplyUnbundUser(Long applyUnbundUser) {
		this.applyUnbundUser = applyUnbundUser;
	}

	public void setApplyUnbundTime(Date applyUnbundTime) {
		this.applyUnbundTime = applyUnbundTime;
	}

	public void setApplyUnbundAgreeTime(Date applyUnbundAgreeTime) {
		this.applyUnbundAgreeTime = applyUnbundAgreeTime;
	}

	public void setApplyUnbundRejectTime(Date applyUnbundRejectTime) {
		this.applyUnbundRejectTime = applyUnbundRejectTime;
	}
}
