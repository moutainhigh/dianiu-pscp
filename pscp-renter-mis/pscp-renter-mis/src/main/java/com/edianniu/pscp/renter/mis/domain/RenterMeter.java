package com.edianniu.pscp.renter.mis.domain;

/**
 * 租客仪表信息
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:40:14
 */
public class RenterMeter extends BaseDo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long renterId;
	
	private Long buildingId;
	
	private String buildingName;
	// 公司仪表ID
	private Long meterId;
	// 仪表编号
	private String meterNo;
	// 费用占比
	private Double rate;
	// 倍率
	private Integer multiple;
	// 线路名称
	private String lineName;
	
	private Integer switchStatus;

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
