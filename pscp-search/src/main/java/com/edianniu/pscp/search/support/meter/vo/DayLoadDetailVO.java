/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 日负荷明细
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayLoadDetailVO extends BaseReportVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;//日期(yyyyMMdd)
	private Integer period;//时间间隔
	private String time;//时刻(hh:MM:ss)
	private Double load;//负荷
	
	
	public String getDate() {
		return date;
	}
	public Integer getPeriod() {
		return period;
	}
	public String getTime() {
		return time;
	}
	public Double getLoad() {
		return load;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setLoad(Double load) {
		this.load = load;
	}
}
