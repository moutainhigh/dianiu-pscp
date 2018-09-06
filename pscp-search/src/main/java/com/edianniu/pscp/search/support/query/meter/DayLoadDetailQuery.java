package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * DayLoadDetailQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class DayLoadDetailQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
    
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMMdd
    private String period;//时间间隔
	
	public String getMeterId() {
		return meterId;
	}
	public String getDate() {
		return date;
	}
	public String getPeriod() {
		return period;
	}
	
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
