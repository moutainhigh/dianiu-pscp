package com.edianniu.pscp.cs.bean.safetyequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 配电房安全用具新增
 * @author zhoujianjian
 * @date 2017年10月31日 下午1:07:05
 */
public class SaveResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
