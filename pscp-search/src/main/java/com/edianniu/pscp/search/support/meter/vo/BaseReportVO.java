package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

public class BaseReportVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long companyId;//客户ID
	private String meterId;//采集点ID/仪表ID
	private Long createTime;
	private Long updateTime;
	private String subTermCode;//分项编码 B0101
	public String getMeterId() {
		return meterId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public String getSubTermCode() {
		return subTermCode;
	}
	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}
}
