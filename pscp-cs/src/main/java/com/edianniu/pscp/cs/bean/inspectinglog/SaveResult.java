package com.edianniu.pscp.cs.bean.inspectinglog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 设备检视日志保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:49:25
 */
public class SaveResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
