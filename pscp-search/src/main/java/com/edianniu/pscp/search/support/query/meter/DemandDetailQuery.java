package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * DemandDetailQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class DemandDetailQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
   
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMM
    private Integer type;//
    private Integer value;//
	
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
	public Integer getType() {
		return type;
	}
	public Integer getValue() {
		return value;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
