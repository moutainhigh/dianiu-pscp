/**
 * 
 */
package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cyl
 *
 */
public class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date createTime;
	private String createUser;
	private Date modifiedTime;
	private String modifiedUser;
	private Integer deleted=0;
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
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
