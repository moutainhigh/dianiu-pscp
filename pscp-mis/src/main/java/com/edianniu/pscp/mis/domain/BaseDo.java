package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date createTime;
	private Date modifiedTime;
	private String modifiedTimeStr;
	private String createUser="系统";
	private String modifiedUser="系统";
	private int deleted=0;//0正常，-1删除
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getModifiedTimeStr() {
		return modifiedTimeStr;
	}
	public void setModifiedTimeStr(String modifiedTimeStr) {
		this.modifiedTimeStr = modifiedTimeStr;
	}
}
