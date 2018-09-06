package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 计费项目
 * @author zhoujianjian
 * @date 2018年4月9日 下午8:39:03
 */
public class CountItem implements Serializable{

	private static final long serialVersionUID = 1L;
	// 1:尖电费    2:峰电费   3:平电费   4:谷电费 
	private Integer type;
	// 类型名称
	private String typeName;
	// 计费数量（保留两位小数）
	private String countNum;
	// 单价（保留两位小数）
	private String price;
	// 金额（保留两位小数）
	private String charge;
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
	public CountItem(Integer type, String typeName, String countNum, String price, String charge) {
		this.type = type;
		this.typeName = typeName;
		this.countNum = countNum;
		this.price = price;
		this.charge = charge;
	}
	public void setCountNumPriceCharge(String countNum, String price, String charge){
		this.countNum = countNum;
		this.price = price;
		this.charge = charge;
	}
	public CountItem() {
	}
	
	
}
