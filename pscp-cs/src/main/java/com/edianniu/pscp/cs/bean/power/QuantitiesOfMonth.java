package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

public class QuantitiesOfMonth implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 日期
	private String date;
	// 每日用电量
	private String quantity;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public QuantitiesOfMonth(String date, String quantity) {
		this.date = date;
		this.quantity = quantity;
	}

	public QuantitiesOfMonth() {
	}
	
	
}
