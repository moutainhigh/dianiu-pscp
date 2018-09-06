package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.cs.bean.power.Type;

public class LoadVO implements Serializable{

	private static final long serialVersionUID = 1L;
	// 当前负荷
	private String present;
	// 负荷区间范围值
	private List<Type> limits;
	
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public List<Type> getLimits() {
		return limits;
	}
	public void setLimits(List<Type> limits) {
		this.limits = limits;
	}
	
	
}
