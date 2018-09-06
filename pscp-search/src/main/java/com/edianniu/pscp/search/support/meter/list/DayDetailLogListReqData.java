/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayDetailLogListReqData extends BaseListReqData
		implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// yyyyMMdd
	private Long startTime;//yyyyMMdd hh:MM 时间
	private Long endTime;//yyyyMMdd hh:MM 时间

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
