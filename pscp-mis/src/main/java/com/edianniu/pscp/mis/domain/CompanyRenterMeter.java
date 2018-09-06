package com.edianniu.pscp.mis.domain;

/**
 * 企业租客仪表信息
 * 
 * @author yanlin.chen
 * @version V1.0
 */
public class CompanyRenterMeter extends BaseDo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long renterId;// 租客ID
	private String meterId;
	private Integer switchStatus;
	public Long getRenterId() {
		return renterId;
	}
	

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}


	public Integer getSwitchStatus() {
		return switchStatus;
	}


	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}


	public String getMeterId() {
		return meterId;
	}


	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	

}
