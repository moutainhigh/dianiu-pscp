package com.edianniu.pscp.cs.bean.response.power;

import java.util.List;
import com.edianniu.pscp.cs.bean.power.CountItem;
import com.edianniu.pscp.cs.bean.power.MeterReadingItem;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 电费明细
 * @author zhoujianjian
 * @date 2017年12月8日 下午6:31:41
 */
@JSONMessage(messageCode = 2002187)
public class ChargeDetailResponse extends BaseResponse{
	// 电费周期
	private String period;
	// 当月电费合计（阿拉伯数字，保留两位小数）
	private String totalCharge;
	// 当月电费合计（中文数字）
	private String totalChargeOfHan;
	// 电度电费计费方式(0普通       1分时)
	private Integer chargeMode;
	// 抄表项
	private List<MeterReadingItem> meterReadingItems;
	// 核定需量（保留两位小数）
	private String verifyDemand;
	// 实际需量（保留两位小数）
	private String actualDemand;
	// 受电容量（保留两位小数）
	private String electricCapacity;
	// 力调标准（保留两位小数）
	private String standardAdjustRate;
	// 实际力率（保留两位小数）
	private String actualAdjustRate;
	// 计费项目
	private List<CountItem> countItems;
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(String totalCharge) {
		this.totalCharge = totalCharge;
	}
	public String getTotalChargeOfHan() {
		return totalChargeOfHan;
	}
	public void setTotalChargeOfHan(String totalChargeOfHan) {
		this.totalChargeOfHan = totalChargeOfHan;
	}
	public List<MeterReadingItem> getMeterReadingItems() {
		return meterReadingItems;
	}
	public void setMeterReadingItems(List<MeterReadingItem> meterReadingItems) {
		this.meterReadingItems = meterReadingItems;
	}
	public String getVerifyDemand() {
		return verifyDemand;
	}
	public void setVerifyDemand(String verifyDemand) {
		this.verifyDemand = verifyDemand;
	}
	public String getActualDemand() {
		return actualDemand;
	}
	public void setActualDemand(String actualDemand) {
		this.actualDemand = actualDemand;
	}
	public String getElectricCapacity() {
		return electricCapacity;
	}
	public void setElectricCapacity(String electricCapacity) {
		this.electricCapacity = electricCapacity;
	}
	public String getStandardAdjustRate() {
		return standardAdjustRate;
	}
	public void setStandardAdjustRate(String standardAdjustRate) {
		this.standardAdjustRate = standardAdjustRate;
	}
	public String getActualAdjustRate() {
		return actualAdjustRate;
	}
	public void setActualAdjustRate(String actualAdjustRate) {
		this.actualAdjustRate = actualAdjustRate;
	}
	public List<CountItem> getCountItems() {
		return countItems;
	}
	public void setCountItems(List<CountItem> countItems) {
		this.countItems = countItems;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	
	
}
