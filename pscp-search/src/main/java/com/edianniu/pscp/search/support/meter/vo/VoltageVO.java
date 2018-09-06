/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 电压
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class VoltageVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long time;// 
	private Double ua;// A相电压
	private Double ub;// B相电压
	private Double uc;// C相电压
	private Integer uStatus;//0正常，1异常
	private Integer uaStatus;//0正常,1过高,-1偏低
	private Integer ubStatus;//0正常,1过高,-1偏低
	private Integer ucStatus;//0正常,1过高,-1偏低
	public Double getUa() {
		return ua;
	}
	public Double getUb() {
		return ub;
	}
	public Double getUc() {
		return uc;
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
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
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

	

}
