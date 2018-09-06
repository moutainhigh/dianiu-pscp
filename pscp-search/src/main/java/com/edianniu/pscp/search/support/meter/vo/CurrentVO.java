/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 电压电流
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class CurrentVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long time;// 
	
	private Double ia;// A相电流
	private Double ib;// B相电流
	private Double ic;// c相电流
	private Double iUnbalanceDegree;//三相电流不平衡度
	private Double iaUnbalanceDegree;//A相电流不平衡度
	private Double ibUnbalanceDegree;//B相电流不平衡度
	private Double icUnbalanceDegree;//C相电流不平衡度
	
	public Double getIa() {
		return ia;
	}
	public Double getIb() {
		return ib;
	}
	public Double getIc() {
		return ic;
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
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
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
