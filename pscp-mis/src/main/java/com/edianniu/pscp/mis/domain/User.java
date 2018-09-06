package com.edianniu.pscp.mis.domain;
/**
 * 
 * @author cyl
 *
 */
public class User extends BaseDo {
	private static final long serialVersionUID = 1L;
	private Long uid;
	private String identifier;
	private String uickName;
	private String loginName;
	private String mobile;
	private String passwd;	
	private String txImg;
	private Integer age;
	private Integer sex;
	
	private String remark;
	private Integer status;
	
	private Integer isCustomer;
	private Integer isFacilitator;
	private Integer isElectrician;
	private Integer isAdmin;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getUickName() {
		return uickName;
	}
	public void setUickName(String uickName) {
		this.uickName = uickName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getTxImg() {
		return txImg;
	}
	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsCustomer() {
		return isCustomer;
	}
	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
	}
	public Integer getIsFacilitator() {
		return isFacilitator;
	}
	public void setIsFacilitator(Integer isFacilitator) {
		this.isFacilitator = isFacilitator;
	}
	public Integer getIsElectrician() {
		return isElectrician;
	}
	public void setIsElectrician(Integer isElectrician) {
		this.isElectrician = isElectrician;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
