package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 分项用电
 * @author zhoujianjian
 * @date 2018年4月9日 下午8:42:16
 */
public class UseType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String meterNo;
	// 用途分类   1动力  2照明  3空调  4特殊
	private Integer type;
	// 用途名称
	private String typeName;
	// 线路编号
	private String lineName;
	// 用电量
	private String quantity;
	// 倍率
	private Integer multiple;
	// 金额
	private String charge;
	// 费用占比
	private String rate; 
	// 单价 指统一单价，分时用户无此字段
	private String price;
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
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
	
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public UseType(String meterNo, Integer type, String typeName, String rate, String lineName, Integer multiple, String quantity, String charge) {
		this.meterNo = meterNo;
		this.type = type;
		this.typeName = typeName;
		this.rate = rate;
		this.lineName = lineName;
		this.multiple = multiple;
		this.quantity = quantity;
		this.charge = charge;
	}
	
	public UseType() {
	}
	
	/**
	 * 设置类型
	 * @param type
	 * @param typeName
	 */
	public void setTTQC(Integer type, String typeName){
		this.type = type;
		this.typeName = typeName;
	}

}
