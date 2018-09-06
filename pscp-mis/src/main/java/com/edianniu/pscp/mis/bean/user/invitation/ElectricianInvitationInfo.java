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
public class ElectricianInvitationInfo implements Serializable{
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
     * 企业电工主帐号
     */
    private boolean mainAccount=false;
    /**
     * 手机号码
     */
    private String mobile;
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
    private Date createTime;
    private String createUser;
    private Date modifiedTime;
    private String modifiedUser;
    /***电工信息**/
    private Long electricianId;
    private String idCardNo;
    private Date auditTime;
    private Integer authStatus;//
    /**电工名字**/
    private String userName;
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
	public Long getElectricianId() {
		return electricianId;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public Integer getAuthStatus() {
		return authStatus;
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
	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
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
	public boolean isMainAccount() {
		return mainAccount;
	}
	public void setMainAccount(boolean mainAccount) {
		this.mainAccount = mainAccount;
	}
    
    
}
