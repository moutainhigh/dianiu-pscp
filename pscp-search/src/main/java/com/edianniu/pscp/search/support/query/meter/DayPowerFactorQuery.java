package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * DayPowerFactorQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class DayPowerFactorQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
   
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMMdd
	
	public String getMeterId() {
		return meterId;
	}
	public String getDate() {
		return date;
	}
	
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
