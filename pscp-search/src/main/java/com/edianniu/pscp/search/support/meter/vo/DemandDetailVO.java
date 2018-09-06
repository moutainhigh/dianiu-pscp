/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 需量明细15分钟值
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DemandDetailVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;//日期(yyyyMMdd hh:MM)
	private Double power;//功率值
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}
	
}
