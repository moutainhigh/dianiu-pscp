package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 发布需求
 * @author zhoujianjian
 * 2017年9月14日下午4:41:54
 */
public class SaveResult extends Result {
	private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
