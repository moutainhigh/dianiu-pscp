/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 月功率因数信息
 * 1）日期+实际功率因数+力调类型+力调电费
 * 2）实际功率因数=
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class MonthPowerFactorVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;//日期(yyyyMM)
	private Double factor;//实际功率因数
	private Integer forceType;//0 无，1奖励，2惩罚
	private Double forceRate;//力调率
	private Double forceFee;//力调电费
	public String getDate() {
		return date;
	}
	public Double getFactor() {
		return factor;
	}
	public Integer getForceType() {
		return forceType;
	}
	public Double getForceFee() {
		return forceFee;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	public void setForceType(Integer forceType) {
		this.forceType = forceType;
	}
	public void setForceFee(Double forceFee) {
		this.forceFee = forceFee;
	}
	public Double getForceRate() {
		return forceRate;
	}
	public void setForceRate(Double forceRate) {
		this.forceRate = forceRate;
	}
}
