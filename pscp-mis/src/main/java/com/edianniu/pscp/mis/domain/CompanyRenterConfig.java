package com.edianniu.pscp.mis.domain;

/**
 * 企业租客配置信息
 * 
 * @author yanlin.chen
 * @version V1.0
 */
public class CompanyRenterConfig extends BaseDo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 租客ID
	 */
	private Long renterId;
	/**
	 * 预缴费租客余额提醒临界值
	 */
	private Double amountLimit;
	// 1:预缴费(默认) 2:月结算
	private Integer chargeMode = 1;
	
	/**
	 * 仪表状态
	 */
	private Integer switchStatus;// 0:开（默认） 1：合

	public Long getId() {
		return id;
	}

	public Long getRenterId() {
		return renterId;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Double getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Double amountLimit) {
		this.amountLimit = amountLimit;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

}
