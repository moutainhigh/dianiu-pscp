package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.VoltageVO;

/**
 * 门户：智能监控>监控列表>电压健康
 * @author zhoujianjian
 * @date 2017年12月15日 下午4:11:06
 */
public class SafetyVoltageResult extends Result{

	private static final long serialVersionUID = 1L;
	// 电压合格率
	private String rateOfQualified;
	// 合格上限值
	private String upLimit;
	// 合格下限值
	private String downLimit;
	// 实时ua
	private String uaOfNow;
	// 实时ub
	private String ubOfNow;
	// 实时uc
	private String ucOfNow;
	
	private List<VoltageVO> uas;
	
	private List<VoltageVO> ubs;
	
	private List<VoltageVO> ucs;

	public String getRateOfQualified() {
		return rateOfQualified;
	}

	public void setRateOfQualified(String rateOfQualified) {
		this.rateOfQualified = rateOfQualified;
	}

	public String getUpLimit() {
		return upLimit;
	}

	public void setUpLimit(String upLimit) {
		this.upLimit = upLimit;
	}

	public String getDownLimit() {
		return downLimit;
	}

	public void setDownLimit(String downLimit) {
		this.downLimit = downLimit;
	}

	public String getUaOfNow() {
		return uaOfNow;
	}

	public void setUaOfNow(String uaOfNow) {
		this.uaOfNow = uaOfNow;
	}

	public String getUbOfNow() {
		return ubOfNow;
	}

	public void setUbOfNow(String ubOfNow) {
		this.ubOfNow = ubOfNow;
	}

	public String getUcOfNow() {
		return ucOfNow;
	}

	public void setUcOfNow(String ucOfNow) {
		this.ucOfNow = ucOfNow;
	}

	public List<VoltageVO> getUas() {
		return uas;
	}

	public void setUas(List<VoltageVO> uas) {
		this.uas = uas;
	}

	public List<VoltageVO> getUbs() {
		return ubs;
	}

	public void setUbs(List<VoltageVO> ubs) {
		this.ubs = ubs;
	}

	public List<VoltageVO> getUcs() {
		return ucs;
	}

	public void setUcs(List<VoltageVO> ucs) {
		this.ucs = ucs;
	}
	
	

}
