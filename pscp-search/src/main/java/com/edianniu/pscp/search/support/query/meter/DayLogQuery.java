package com.edianniu.pscp.search.support.query.meter;

import com.edianniu.pscp.search.common.BaseQuery;

/**
 * DayLogQuery
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午5:06:34 
 * @version V1.0
 */
public class DayLogQuery extends BaseQuery {
    private static final long serialVersionUID = 1886432885693316850L;
    private Long companyId;//客户ID
    private String meterId;//采集点
    private String date;//yyyyMMdd
    private Integer uStatus;//0正常，1异常
   	private Integer uaStatus;//0正常,1过高,-1偏低
   	private Integer ubStatus;//0正常,1过高,-1偏低
   	private Integer ucStatus;//0正常,1过高,-1偏低
   	private Double iUnbalanceDegree;//三相电流不平衡度
   	private Double iaUnbalanceDegree;//A相电流不平衡度
   	private Double ibUnbalanceDegree;//B相电流不平衡度
   	private Double icUnbalanceDegree;//C相电流不平衡度
	public String getMeterId() {
		return meterId;
	}
	public String getDate() {
		return date;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
