package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * DayAggregateListReqData
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午12:22:16 
 * @version V1.0
 */
public class DayAggregateListReqData extends BaseListReqData implements Serializable {
    private static final long serialVersionUID = -101315817790932729L;
    private String date;
    //查询电压是否正常
	private Integer uStatus;//0正常，1异常
	private Integer uaStatus;//0正常,1过高,-1偏低
	private Integer ubStatus;//0正常,1过高,-1偏低
	private Integer ucStatus;//0正常,1过高,-1偏低
	//查询电流平衡度是不正常的情况，当前值是标准值，采集的数据大于当前值，就算电流失衡
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
