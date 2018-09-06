package com.edianniu.pscp.cs.bean.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 消防设施保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:36:55
 */
public class SaveResult extends Result{

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
