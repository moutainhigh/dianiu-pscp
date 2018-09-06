package com.edianniu.pscp.cs.bean.room.vo;

import java.io.Serializable;

/**
 * 
 * @author zhoujianjian
 * 2017年9月11日下午9:46:35
 */
public class RoomVO implements Serializable{
	private static final long serialVersionUID = 1L;
	 
	//配电房主键
	private Long id;
	
	//所属公司ID
	private Long companyId;
	 
	//配电房名称
	private String name;
	 
	//配电房创建时间(yyyy-MM-dd)
	private String createTime;
	
	//配电房负责人
	private String director;
	
	//配电房联系方式
	private String contactNumber;
	
	//配电房地址
	private String address;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	 
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
