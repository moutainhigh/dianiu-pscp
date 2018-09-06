package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * DayLoadDetailQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class DayDetailLogQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
    
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMMdd
    private Integer period;//时间间隔
    private Long startTime;//yyyyMMdd hh:MM 时间
	private Long endTime;//yyyyMMdd hh:MM 时间
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getMeterId() {
		return meterId;
	}
	public String getDate() {
		return date;
	}
	public Integer getPeriod() {
		return period;
	}
	
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setDate(String date) {
		this.date = date;
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
