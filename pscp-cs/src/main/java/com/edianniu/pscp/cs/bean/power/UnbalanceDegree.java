package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 门户：智能监控>监控列表>电流平衡
 * 不平衡度
 * @author zhoujianjian
 * @date 2017年12月16日 上午10:02:02
 */
public class UnbalanceDegree implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String time;
	// 不平衡度
	private String value;
	// a相电流
	private String ia;
	// b相电流
	private String ib;
	// c相电流
	private String ic;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIa() {
		return ia;
	}

	public void setIa(String ia) {
		this.ia = ia;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public UnbalanceDegree(String time, String value, String ia, String ib, String ic) {
		this.time = time;
		this.value = value;
		this.ia = ia;
		this.ib = ib;
		this.ic = ic;
	}

	public UnbalanceDegree() {
	}
	
	

}
