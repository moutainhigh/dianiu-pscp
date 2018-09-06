package com.edianniu.pscp.cs.bean.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 配电房客户操作记录新增和编辑
 * @author zhoujianjian
 * @date 2017年10月18日 下午5:20:37
 */
public class SaveResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
