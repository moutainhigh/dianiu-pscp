package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * 租客配置信息
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:40:14
 */
public class RenterConfig extends BaseDo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long renterId;
	// 0:开闸(默认)   1:合闸
	private Integer switchStatus = 0;
	// 1:预缴费(默认)   2:月结算
	private Integer chargeMode = 1;
	
	private Date startTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
}
