package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * MeterLogQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class MeterLogQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
 
    private Long companyId;//客户ID
    private String meterId;//采集点
    private Long startTime;//查询区间
    private Long endTime;//查询区间
    private Integer collapseSize=1;//默认折叠数量
	public String getMeterId() {
		return meterId;
	}
	
	public void setMeterId(String meterId) {
		this.meterId = meterId;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCollapseSize() {
		return collapseSize;
	}

	public void setCollapseSize(Integer collapseSize) {
		this.collapseSize = collapseSize;
	}
}
