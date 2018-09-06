package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 租客仪表
 * @author zhoujianjian
 * @date 2018年3月29日 下午4:17:55
 */
public class RenterMeterInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 此处指公司仪表ID，而非租客仪表表的主键
	private Long id;
	
	// 仪表名称
	private String name;
	
	// 费用占比
	private Double rate;
	
	// 楼宇ID
	private Long buildingId;
	
	private String buildingName;
	
	//线路名称
	private String lineName;
	
	//1:动力2:照明3:空调4:特殊
	private Integer type;
	
	private String typeName;
	
	//闸门状态（ 0：开闸   1：合闸    2：跳闸中   3： 合闸中）
	private Integer switchStatus;
	
	//是否是公摊仪表    0:否       1:是
	private Integer isCommon;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Integer getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Integer isCommon) {
		this.isCommon = isCommon;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

}
