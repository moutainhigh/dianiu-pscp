package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 计费项目
 * @author zhoujianjian
 * @date 2017年12月8日 下午6:40:35
 */
public class CountItem implements Serializable{

	private static final long serialVersionUID = 1L;
	// 1:尖电费    2:峰电费   3:平电费   4:谷电费    5:基本电费    6:力调电费   
	private Integer type;
	// 类型名称
	private String typeName;
	// 计费数量（保留两位小数）
	private String countNum;
	// 单价（保留两位小数）
	private String price;
	// 金额（保留两位小数）
	private String charge;
	// 备注
	private String remark;
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
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CountItem(Integer type, String typeName, String countNum, String price, String charge, String remark) {
		this.type = type;
		this.typeName = typeName;
		this.countNum = countNum;
		this.price = price;
		this.charge = charge;
		this.remark = remark;
	}
	public CountItem() {
	}
	
	public void setCountNumPriceCharge(String countNum, String price, String charge){
		this.countNum = countNum;
		this.price = price;
		this.charge = charge;
	}
	
	

}
