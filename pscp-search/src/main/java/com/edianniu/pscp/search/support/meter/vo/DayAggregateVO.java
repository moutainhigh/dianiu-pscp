/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 日实时数据
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayAggregateVO  extends BaseReportVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;//日期(yyyyMMdd)
	//当前最新信息
	private Double currentLoad;//当前负荷
	private Double maxLoad;//最高负荷
	private Double electric;//今日用电量
	//当前最新电压和电流信息(根据间隔时间来吧)
	private Double ua;// A相电压
	private Double ub;// B相电压
	private Double uc;// C相电压
	private Integer uStatus;//0正常，1异常
	private Integer uaStatus;//0正常,1过高,-1偏低
	private Integer ubStatus;//0正常,1过高,-1偏低
	private Integer ucStatus;//0正常,1过高,-1偏低
	private Double ia;// A相电流
	private Double ib;// B相电流
	private Double ic;// C相电流
	private Double iUnbalanceDegree;//三相电流不平衡度
	private Double iaUnbalanceDegree;//A相电流不平衡度
	private Double ibUnbalanceDegree;//B相电流不平衡度
	private Double icUnbalanceDegree;//C相电流不平衡度
	public String getDate() {
		return date;
	}
	
	
	public void setDate(String date) {
		this.date = date;
	}
	public Double getCurrentLoad() {
		return currentLoad;
	}
	public Double getMaxLoad() {
		return maxLoad;
	}
	public Double getElectric() {
		return electric;
	}
	public void setCurrentLoad(Double currentLoad) {
		this.currentLoad = currentLoad;
	}
	public void setMaxLoad(Double maxLoad) {
		this.maxLoad = maxLoad;
	}
	public void setElectric(Double electric) {
		this.electric = electric;
	}


	public Double getUa() {
		return ua;
	}


	public Double getUb() {
		return ub;
	}


	public Double getUc() {
		return uc;
	}


	public Integer getuStatus() {
		return uStatus;
	}


	public Integer getUaStatus() {
		return uaStatus;
	}


	public Integer getUbStatus() {
		return ubStatus;
	}


	public Integer getUcStatus() {
		return ucStatus;
	}


	public Double getIa() {
		return ia;
	}


	public Double getIb() {
		return ib;
	}


	public Double getIc() {
		return ic;
	}


	public Double getiUnbalanceDegree() {
		return iUnbalanceDegree;
	}


	public Double getIaUnbalanceDegree() {
		return iaUnbalanceDegree;
	}


	public Double getIbUnbalanceDegree() {
		return ibUnbalanceDegree;
	}


	public Double getIcUnbalanceDegree() {
		return icUnbalanceDegree;
	}


	public void setUa(Double ua) {
		this.ua = ua;
	}


	public void setUb(Double ub) {
		this.ub = ub;
	}


	public void setUc(Double uc) {
		this.uc = uc;
	}


	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}


	public void setUaStatus(Integer uaStatus) {
		this.uaStatus = uaStatus;
	}


	public void setUbStatus(Integer ubStatus) {
		this.ubStatus = ubStatus;
	}


	public void setUcStatus(Integer ucStatus) {
		this.ucStatus = ucStatus;
	}


	public void setIa(Double ia) {
		this.ia = ia;
	}


	public void setIb(Double ib) {
		this.ib = ib;
	}


	public void setIc(Double ic) {
		this.ic = ic;
	}


	public void setiUnbalanceDegree(Double iUnbalanceDegree) {
		this.iUnbalanceDegree = iUnbalanceDegree;
	}


	public void setIaUnbalanceDegree(Double iaUnbalanceDegree) {
		this.iaUnbalanceDegree = iaUnbalanceDegree;
	}


	public void setIbUnbalanceDegree(Double ibUnbalanceDegree) {
		this.ibUnbalanceDegree = ibUnbalanceDegree;
	}


	public void setIcUnbalanceDegree(Double icUnbalanceDegree) {
		this.icUnbalanceDegree = icUnbalanceDegree;
	}
	
	
}
