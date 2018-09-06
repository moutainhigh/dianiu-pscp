/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 日功率因数-实时更新
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayPowerFactorVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;//日期(yyyyMMdd)
	private Double factor;//实际功率因数
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
}
