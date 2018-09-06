package com.edianniu.pscp.cs.bean.dutylog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 配电房值班日志删除
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:36:04
 */
public class DeleteResult extends Result {

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
