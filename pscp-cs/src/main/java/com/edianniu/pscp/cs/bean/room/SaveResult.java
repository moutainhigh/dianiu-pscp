package com.edianniu.pscp.cs.bean.room;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午1:14:00
 */
public class SaveResult  extends Result{
	private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
