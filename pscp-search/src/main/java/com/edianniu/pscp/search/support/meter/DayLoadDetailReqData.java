/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayLoadDetailReqData extends AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;

	private String date;//日期(yyyyMMdd)
	private String period;//时间间隔
	private String time;//时刻
	private Double load;//负荷
	
	@Override
	public String getId() {//主键
		
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+getDate()+"#"+getPeriod()+"#"+this.getTime();
	}
	
	public String getDate() {
		return date;
	}
	public String getPeriod() {
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
	public void setPeriod(String period) {
		this.period = period;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setLoad(Double load) {
		this.load = load;
	}
	
	
}
