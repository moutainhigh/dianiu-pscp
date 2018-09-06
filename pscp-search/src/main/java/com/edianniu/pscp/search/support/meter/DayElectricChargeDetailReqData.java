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
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayElectricChargeDetailReqData extends AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;// 日期(yyyyMMdd)
	private Integer period;//时间间隔
	private Long startTime;//抄表开始时间
	private Long endTime;//抄表截止时间
	private Double start;//开始抄表电量  kwh;
	private Double end;//结束抄表电量  kwh;
	private Integer rate;//倍率
	private Double electric;//电量 kwh
	private Integer chargeType;//(0 默认，尖1，峰2，平3，谷4)分段类型 subSectionType
	private Double price;//电价
	private Double fee;//电费 元
	private Double factor;//功率因数
	
	@Override
	public String getId() {//主键
		String hhMM=DateUtils.format(new Date(getStartTime()), "HHmm");
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+getDate()+"#"+hhMM;
	}

	public String getDate() {
		return date;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}
	public Double getStart() {
		return start;
	}
	public Double getEnd() {
		return end;
	}
	public Integer getRate() {
		return rate;
	}
	public Double getElectric() {
		return electric;
	}
	public Integer getChargeType() {
		return chargeType;
	}
	public Double getPrice() {
		return price;
	}
	public Double getFee() {
		return fee;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public void setStart(Double start) {
		this.start = start;
	}
	public void setEnd(Double end) {
		this.end = end;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public void setElectric(Double electric) {
		this.electric = electric;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}
}
