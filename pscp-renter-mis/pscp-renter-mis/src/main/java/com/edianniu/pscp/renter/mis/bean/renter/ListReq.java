package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

import com.edianniu.pscp.renter.mis.commons.Constants;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:51:13
 */
public class ListReq implements Serializable{

	private static final long serialVersionUID = 1L;
	// 客户uid
	private Long uid;
	// 租客电话
	private String mobile;
	
	private String name;
	
	private Integer status;
	// yyyy-MM-dd
	private String sdate;
	// yyyy-MM-dd
	private String bdate;
	
	private Integer offset;
	
	private Integer limit = Constants.DEFAULT_PAGE_SIZE;
	
	// 1.预欠费租客列表     2.待缴费租客列表    3.所有租客列表
	private Integer type;
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
