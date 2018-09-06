package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

/**
 * 系统用户
 */
public class SysUserInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 密码
	 */
	private transient String passwd;
	private String txImg;
	private String nickName;
	private int sex;
	private int age;
	private String remark;
	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态 0：禁用 1：正常
	 */
	private Integer status;
	// 需求审核通知  0：不通知（默认）；1：通知
	private Integer needsAuditNotice;
	// 会员审核通知  0：不通知（默认）；1：通知
	private Integer memberAuditNotice;
	// 掉线通知  0：不通知（默认）；1：通知
	private Integer offLineNotice;
	
	/**
	 * 设置：
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 * 
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取：用户名
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置：邮箱
	 * 
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 * 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置：手机号
	 * 
	 * @param mobile
	 *            手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 * 
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置：状态 0：禁用 1：正常
	 * 
	 * @param status
	 *            状态 0：禁用 1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态 0：禁用 1：正常
	 * 
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
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

	public String getNickName() {
		return nickName;
	}

	public int getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNeedsAuditNotice() {
		return needsAuditNotice;
	}

	public void setNeedsAuditNotice(Integer needsAuditNotice) {
		this.needsAuditNotice = needsAuditNotice;
	}

	public Integer getMemberAuditNotice() {
		return memberAuditNotice;
	}

	public void setMemberAuditNotice(Integer memberAuditNotice) {
		this.memberAuditNotice = memberAuditNotice;
	}

	public Integer getOffLineNotice() {
		return offLineNotice;
	}

	public void setOffLineNotice(Integer offLineNotice) {
		this.offLineNotice = offLineNotice;
	}
}
