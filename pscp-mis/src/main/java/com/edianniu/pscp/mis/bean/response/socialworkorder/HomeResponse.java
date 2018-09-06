package com.edianniu.pscp.mis.bean.response.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

@JSONMessage(messageCode = 2002013)
public final class HomeResponse extends BaseResponse {
	private Integer totalOrderCount	;//总接单数
	private String totalIncome;//总收入0.00元
	private Long onlineTime;//在线时长(秒)
	private int isOnline;//0否，1是；是否在线
	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public Long getOnlineTime() {
		return onlineTime;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
