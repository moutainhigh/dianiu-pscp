package com.edianniu.pscp.cs.bean.room;

import java.util.Date;

import com.edianniu.pscp.cs.commons.BaseQuery;

/**
 * 
 * @author zhoujianjian
 * 2017年9月11日下午10:38:49
 */
public class ListQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	private Long companyId;
	
	private String name;
	
	private String director;
	
	private String contactNumber;
	
	private Date startTime;
	
	private Date endTime;

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
