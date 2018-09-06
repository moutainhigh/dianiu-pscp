package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;
/**
 * 取消需求
 * @author zhoujianjian
 * 2017年9月15日上午12:06:54
 */
public class CancelResult extends Result {

	private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
