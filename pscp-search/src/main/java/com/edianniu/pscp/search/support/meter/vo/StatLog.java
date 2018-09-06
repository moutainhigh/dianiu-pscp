/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 电量电费数据统计
 * 
 * @author yanlin.chen yanlin.chen@edianniu.com
 * @version V1.0 2017年12月5日 下午4:45:48
 */
public class StatLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// 日期(yyyyMM|yyyyMMdd|yyyy)
	private String meterId;// 仪表ID
	private Double total;// 总电量
	private Double lastTotal;// 上期读数
	private Double thisTotal;// 本期读数
	private Double apex;// 尖电量，
	private Double lastApex;// 上期读数
	private Double thisApex;// 本期读数
	private Double peak;// 峰电量
	private Double lastPeak;// 上期读数
	private Double thisPeak;// 本期读数
	private Double flat;// 平电量
	private Double lastFlat;// 上期读数
	private Double thisFlat;// 本期读数
	private Double valley;// 谷电量
	private Double lastValley;// 上期读数
	private Double thisValley;// 本期读数
	private String subTermCode;// 分项编码 转换为：分项类型-1:动力设备2:照明设备 3:空调设备 4:特殊设备
	private Integer rate;// 倍率
	public String getDate() {
		return date;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getSubTermCode() {
		return subTermCode;
	}

	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}

	public Double getLastTotal() {
		return lastTotal;
	}

	public Double getThisTotal() {
		return thisTotal;
	}

	public Double getLastApex() {
		return lastApex;
	}

	public Double getThisApex() {
		return thisApex;
	}

	public Double getLastPeak() {
		return lastPeak;
	}

	public Double getThisPeak() {
		return thisPeak;
	}

	public Double getLastFlat() {
		return lastFlat;
	}

	public Double getThisFlat() {
		return thisFlat;
	}

	public Double getLastValley() {
		return lastValley;
	}

	public Double getThisValley() {
		return thisValley;
	}

	public void setLastTotal(Double lastTotal) {
		this.lastTotal = lastTotal;
	}

	public void setThisTotal(Double thisTotal) {
		this.thisTotal = thisTotal;
	}

	public void setLastApex(Double lastApex) {
		this.lastApex = lastApex;
	}

	public void setThisApex(Double thisApex) {
		this.thisApex = thisApex;
	}

	public void setLastPeak(Double lastPeak) {
		this.lastPeak = lastPeak;
	}

	public void setThisPeak(Double thisPeak) {
		this.thisPeak = thisPeak;
	}

	public void setLastFlat(Double lastFlat) {
		this.lastFlat = lastFlat;
	}

	public void setThisFlat(Double thisFlat) {
		this.thisFlat = thisFlat;
	}

	public void setLastValley(Double lastValley) {
		this.lastValley = lastValley;
	}

	public void setThisValley(Double thisValley) {
		this.thisValley = thisValley;
	}

	public Double getTotal() {
		return total;
	}

	public Double getApex() {
		return apex;
	}

	public Double getPeak() {
		return peak;
	}

	public Double getFlat() {
		return flat;
	}

	public Double getValley() {
		return valley;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setApex(Double apex) {
		this.apex = apex;
	}

	public void setPeak(Double peak) {
		this.peak = peak;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public void setValley(Double valley) {
		this.valley = valley;
	}
}
