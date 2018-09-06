package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;
public class CollectorPointVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 监测点名称
	private String name;
	// 功率因数
	private String powerFactor;
	// 设备类型(当该监测点所在线路为父级线路时，type为空)
    // 1:动力   2:照明   3:空调   4:特殊
	private Integer type;
	// 设备类型名称
	private String typeName;
	// 设备地址(当该监测点所在线路为父级线路时，address为空)
	private String address;
	// 当日最高负荷---门户-总览
	private String maxLoadOfToday;
	// 当日用电量----门户-总览
	private String quantityOfToday;
	// 监测时段的用电量用电量---门户-峰谷利用
	private String quantity;
	// 监测时段的用电量电费---门户-峰谷利用
	private String charge;
	// 绑定的线路ID
	private Long lineId;

	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
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
	public String getPowerFactor() {
		return powerFactor;
	}
	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaxLoadOfToday() {
		return maxLoadOfToday;
	}
	public void setMaxLoadOfToday(String maxLoadOfToday) {
		this.maxLoadOfToday = maxLoadOfToday;
	}
	public String getQuantityOfToday() {
		return quantityOfToday;
	}
	public void setQuantityOfToday(String quantityOfToday) {
		this.quantityOfToday = quantityOfToday;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

}
