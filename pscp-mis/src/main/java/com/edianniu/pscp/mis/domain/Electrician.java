/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:37 
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 电工domain
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:37 
 * @version V1.0
 */
public class Electrician extends BaseDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//
	private String identifier;//编号	 
	private Long memberId;//会员ID
	private String userName;//用户真实姓名	 
	private String idCardNo;//身份证号码
	private String idCardFrontImg;//身份证正面照片	 
	private String idCardBackImg;//身份证反面照片	  	 	 
	
	private int status;//状态 0:认证中,1:认证通过,-1认证失败
	private String remark;//备注	 
	private int workStatus;//工作状态0:上班,1:下班
	private Long workTime;//在线时间
	private Long companyId;//企业ID默认0：社会电工，其他是指企业的电工
	private Date auditTime;//审核时间
	private String auditUser;//审核者
	/**
	 * 是否社会电工
	 * @return
	 */
	public boolean isSocialElectrician(){
		if(companyId==0){
			return true;
		}
		return false;
	}
	public Long getId() {
		return id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public Long getMemberId() {
		return memberId;
	}
	public String getUserName() {
		return userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}
	public String getIdCardBackImg() {
		return idCardBackImg;
	}

	public int getStatus() {
		return status;
	}
	public String getRemark() {
		return remark;
	}
	public int getWorkStatus() {
		return workStatus;
	}
	public Long getWorkTime() {
		return workTime;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}
	public void setIdCardBackImg(String idCardBackImg) {
		this.idCardBackImg = idCardBackImg;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}
	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	
	
}
