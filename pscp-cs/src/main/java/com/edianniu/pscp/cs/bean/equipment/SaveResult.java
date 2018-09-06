package com.edianniu.pscp.cs.bean.equipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 
 * @author zhoujianjian
 * 2017年9月28日下午4:24:11
 */
public class SaveResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
