package com.edianniu.pscp.renter.mis.domain;

public class CompanyMeter extends BaseDo {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long companyId;
	// 采集点编号   即采集网关设备所对应的仪表ID
	private String meterId;
	// 仪表状态   0:下线(默认)  1:上线   2:其他状态
	private Integer status;
	// 与线路的绑定状态状态   0:未绑定(默认)  1:绑定
	private Integer bindStatus;
	// 倍率
	private Integer multiple;
	// 费用占比系数，默认为0，当费用占比超过100%时该系数为1
	private Integer allot;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Integer getAllot() {
		return allot;
	}
	public void setAllot(Integer allot) {
		this.allot = allot;
	}
	
	
}
