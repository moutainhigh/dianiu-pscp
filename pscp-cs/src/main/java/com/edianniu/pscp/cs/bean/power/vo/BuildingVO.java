package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

public class BuildingVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	// 用能排行的名次
	private Integer rank;
	// 当月用电量
	private String quantityOfThisMonth;
	// 上月用电量
	private String quantityOfLastMonth;
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
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getQuantityOfThisMonth() {
		return quantityOfThisMonth;
	}
	public void setQuantityOfThisMonth(String quantityOfThisMonth) {
		this.quantityOfThisMonth = quantityOfThisMonth;
	}
	public String getQuantityOfLastMonth() {
		return quantityOfLastMonth;
	}
	public void setQuantityOfLastMonth(String quantityOfLastMonth) {
		this.quantityOfLastMonth = quantityOfLastMonth;
	}
	

}
