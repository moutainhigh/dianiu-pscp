package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

/**
 * 智能监控>监控列表
 * @author zhoujianjian
 * @date 2017年12月12日 下午2:13:10
 */
public class MonitorVO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 客户ID
	private Long companyId;
	// 客户名称
	private String companyName;
	// 异常数量
	private Integer warningsNum;
	// 当前负荷
	private String loadOfNow;
	// 今日最高负荷
	private String maxLoadOfToday;
	// 今日用电量
	private String electricQuantity;
	// 企业图像
	private String txImg;
	
	public String getTxImg() {
		return txImg;
	}
	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getWarningsNum() {
		return warningsNum;
	}
	public void setWarningsNum(Integer warningsNum) {
		this.warningsNum = warningsNum;
	}
	public String getLoadOfNow() {
		return loadOfNow;
	}
	public void setLoadOfNow(String loadOfNow) {
		this.loadOfNow = loadOfNow;
	}
	public String getMaxLoadOfToday() {
		return maxLoadOfToday;
	}
	public void setMaxLoadOfToday(String maxLoadOfToday) {
		this.maxLoadOfToday = maxLoadOfToday;
	}
	public String getElectricQuantity() {
		return electricQuantity;
	}
	public void setElectricQuantity(String electricQuantity) {
		this.electricQuantity = electricQuantity;
	}
	
	

}
