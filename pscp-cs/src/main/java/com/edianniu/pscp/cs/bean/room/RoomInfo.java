package com.edianniu.pscp.cs.bean.room;

import java.io.Serializable;
import java.util.Date;
/**
 * @author zhoujianjian
 * 2017年9月12日下午10:55:07
 */
public class RoomInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//配电房主键
	private Long id;
	//所属CompanyId
	private Long companyId;
	//配电房名称
	private String name;
	//负责人
	private String director;
	//联系方式
	private String contactNumber;
	//地址
	private String address;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifiedTime;
	//创建人
	private String createUser;
	//修改人
	private String modifiedUser;
	//是否删除（0：未删除   -1:删除）
	private Integer deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
	
	
	
}
