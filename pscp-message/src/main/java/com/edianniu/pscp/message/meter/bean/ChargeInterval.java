/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月5日 下午6:23:45 
 * @version V1.0
 */
package com.edianniu.pscp.message.meter.bean;

import java.io.Serializable;

/**
 * 计费区间
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月5日 下午6:23:45
 * @version V1.0
 */
public class ChargeInterval implements Serializable {
	private static final long serialVersionUID = 1L;
	private String interval;
	private Long startTime;
	private Long endTime;
	private Integer intervalType;
	private Double intervalPrice;
	private String intervalStr;
	/**
	 * 判断当前时间范围是不是在区间内
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean include(Long time){
		if(time>=this.startTime&&time<=this.endTime){
			return true;
		}
		return false;
	}
	public Long getStartTime() {
		return startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getIntervalType() {
		return intervalType;
	}
	public Double getIntervalPrice() {
		return intervalPrice;
	}
	public void setIntervalType(Integer intervalType) {
		this.intervalType = intervalType;
	}
	public void setIntervalPrice(Double intervalPrice) {
		this.intervalPrice = intervalPrice;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getIntervalStr() {
		return intervalStr;
	}
	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}
}
