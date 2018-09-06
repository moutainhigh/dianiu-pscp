package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * MonthElectricQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:07 
 * @version V1.0
 */
public class MonthElectricQuery extends BaseQuery {
   
	private static final long serialVersionUID = 1886432885693316850L;
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMM
    
	
	public String getMeterId() {
		return meterId;
	}
	public String getDate() {
		return date;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setDate(String date) {
		this.date = date;
	}
    
}
