package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 需求列表
 * @author zhoujianjian
 * 2017年9月17日下午9:34:16
 */
public class ListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private int offset;
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	//需求状态："verifying"（审核中）  "responding"（响应中）  ''quoting"（报价中）   "finished"（已结束）
	private String status;
	
	private String name;
	
	private String orderId;
	
	private String publishTime;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	

}
