package com.edianniu.pscp.cs.bean.power;

import com.edianniu.pscp.cs.bean.Result;

public class HasDataResult extends Result{

	private static final long serialVersionUID = 1L;
	
	// 仪表是否拥有数据   0：没有     1：有
	private Integer hasData;

	public Integer getHasData() {
		return hasData;
	}

	public void setHasData(Integer hasData) {
		this.hasData = hasData;
	}
	
	

}
