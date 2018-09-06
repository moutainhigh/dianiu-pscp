/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * DayVoltageCurrentDetailListReqData
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayVoltageCurrentDetailListReqData extends BaseListReqData
		implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// 日期
	private Integer period;//区间 默认15分钟

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

}
