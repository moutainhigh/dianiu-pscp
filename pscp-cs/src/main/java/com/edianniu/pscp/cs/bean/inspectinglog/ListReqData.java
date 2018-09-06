package com.edianniu.pscp.cs.bean.inspectinglog;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 客户设备检视日志列表
 * @author zhoujianjian
 * @date 2017年11月2日 上午11:57:52
 */
public class ListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private int offset;
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	private Long equipmentId;
	private Integer type;

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
	public Long getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
