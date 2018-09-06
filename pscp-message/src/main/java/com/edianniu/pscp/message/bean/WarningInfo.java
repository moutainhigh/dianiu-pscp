package com.edianniu.pscp.message.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 智能终端--客户设备告警
 */
public class WarningInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String meterId;
	// 告警类型，如：设备掉线、电压偏高等
	private Integer warningType;
	// 发生时间
	private Date occurTime;
	// 修复时间
	private Date revertTime;
	// 当前值
	private String valueOfNow;
	// 处理状态  	0:未处理  1:已处理
	private Integer dealStatus;

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public Date getRevertTime() {
		return revertTime;
	}

	public void setRevertTime(Date revertTime) {
		this.revertTime = revertTime;
	}
	
	public String getValueOfNow() {
		return valueOfNow;
	}

	public void setValueOfNow(String valueOfNow) {
		this.valueOfNow = valueOfNow;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Integer getWarningType() {
		return warningType;
	}

	public void setWarningType(Integer warningType) {
		this.warningType = warningType;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
}
