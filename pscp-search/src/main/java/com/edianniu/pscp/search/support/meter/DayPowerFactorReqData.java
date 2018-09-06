/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * 日功率因数明细
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayPowerFactorReqData extends AbstractReportReq implements
		Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// 日期(yyyyMMdd)
	private Double factor;// 实际功率因数

	@Override
	public String getId() {// 主键
		return super.getCompanyId() + "#"
				+ super.getMeterId() + "#" + this.getDate();
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

}
