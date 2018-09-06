package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 确认合作
 * @author zhoujianjian
 * 2017年9月15日上午11:58:33
 */
public class ConfirmCooperationResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
