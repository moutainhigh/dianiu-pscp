/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月17日 下午4:30:29 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月17日 下午4:30:29 
 * @version V1.0
 */
public class CompanyInvitationInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/***********邀请信息************/
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
     * 0(邀请，等待客户同意)
     * 1(已绑定，客户已同意)
	 * -1(拒绝)
     */
    private Integer status;
    private Date invitationTime;//最近邀请时间
    private Date invitationAgreeTime;//最近邀请同意时间
    private Date invitationRejectTime;//最近邀请拒绝时间
    private Date createTime;
    private String createUser;
    private Date modifiedTime;
    private String modifiedUser;
    
    /***企业信息**/
    private Long companyId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业认证时间
     */
    private Date auditTime;
    /**
     * 企业认证状态
     */
    private Integer authStatus;
    /**
     * 申请人姓名
     */
    private String userName;
    /**
     * 联系人手机号码
     */
    private String contactMobile;
    /**
     * 联系人名字
     */
    private String contactName;
    /**
     * 企业联系电话
     */
    private String phone;
    /***会员信息**/
    private Integer memberStatus;//-1表示非会员
    
	public Long getId() {
		return id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public String getMobile() {
		return mobile;
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
	public Long getCompanyId() {
		return companyId;
	}
	
	public Date getAuditTime() {
		return auditTime;
	}
	public Integer getAuthStatus() {
		return authStatus;
	}
	public String getPhone() {
		return phone;
	}
	public String getUserName() {
		return userName;
	}
	
	
	public Integer getMemberStatus() {
		return memberStatus;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public String getContactName() {
		return contactName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
    
    
}
