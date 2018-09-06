package com.edianniu.pscp.cs.bean.equipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;
/**
 * 配电房设备删除
 * @author zhoujianjian
 * 2017年9月29日下午10:37:22
 */
public class DeleteResult extends Result {

	private static final long serialVersionUID = 1L;
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
