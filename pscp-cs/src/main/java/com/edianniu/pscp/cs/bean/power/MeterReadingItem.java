package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 抄表项
 * @author zhoujianjian
 * @date 2017年12月8日 下午6:34:41
 */
public class MeterReadingItem implements Serializable{

	private static final long serialVersionUID = 1L;
	// 1:有功（总） 2:有功（尖）  3:有功（峰）   4:有功（平）  5:有功（谷）   6:无功（总）     
	private Integer type;
	// type名称
	private String typeName;
	// 本期示数(保留两位小数)
	private String thisPeriodReading;
	// 上期示数(保留两位小数)
	private String lastPeriodReading;
	// 倍率
	private Integer multiple;
	// 电量(保留两位小数)
	private String quantity;
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
	public String getThisPeriodReading() {
		return thisPeriodReading;
	}
	public void setThisPeriodReading(String thisPeriodReading) {
		this.thisPeriodReading = thisPeriodReading;
	}
	public String getLastPeriodReading() {
		return lastPeriodReading;
	}
	public void setLastPeriodReading(String lastPeriodReading) {
		this.lastPeriodReading = lastPeriodReading;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public MeterReadingItem(Integer type, String typeName, String thisPeriodReading, String lastPeriodReading,
			Integer multiple, String quantity) {
		this.type = type;
		this.typeName = typeName;
		this.thisPeriodReading = thisPeriodReading;
		this.lastPeriodReading = lastPeriodReading;
		this.multiple = multiple;
		this.quantity = quantity;
	}
	public MeterReadingItem() {
	}
	
	
}
