package com.edianniu.pscp.cs.bean.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;

/**
 * 智能监控--添加客户设备告警
 * @author zhoujianjian
 * @date 2017年12月28日 上午11:05:59
 */
public class WarningSaveResult extends Result{
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
