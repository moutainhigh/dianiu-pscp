/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;
import java.util.Date;

import com.edianniu.pscp.search.util.DateUtils;

/**
 * 日功率因数明细
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayPowerFactorDetailReqData extends AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;//日期(yyyyMMdd)
	private Integer period;//间隔时间 
	private Long startTime;//采集时间 yyyyMMdd hh:MM:ss
	private Long endTime;//采集时间 yyyyMMdd hh:MM:ss
	private Double factor;//实际功率因数
	
	@Override
	public String getId() {//主键
		String timeStr=DateUtils.format(new Date(getStartTime()), "yyyyMMddHHmm");
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+this.getPeriod()+"#"+timeStr;
	}

	public String getDate() {
		return date;
	}

	
	public Double getFactor() {
		return factor;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
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

	

	
	
}
