package com.edianniu.pscp.cs.bean.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 配电房操作设备删除
 * @author zhoujianjian
 * @date 2017年10月29日 下午10:35:48
 */
public class DeleteResult extends Result {

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
