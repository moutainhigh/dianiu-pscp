package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;
import java.util.Set;

public class AvgOfMetreReqData implements Serializable{

private static final long serialVersionUID = 1L;
	
	private Long companyId;
	private Set<String> meterIds;
	private String fromDate;
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Set<String> getMeterIds() {
		return meterIds;
	}
	public void setMeterIds(Set<String> meterIds) {
		this.meterIds = meterIds;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	
}
