package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 需求重新发布
 * @author zhoujianjian
 * 2017年9月14日下午4:41:48
 */
public class RepublishResult extends Result {
private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
