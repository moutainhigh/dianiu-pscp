package com.edianniu.pscp.cs.bean.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 消防设施删除
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:17:07
 */
public class DeleteResult extends Result {

	private static final long serialVersionUID = 1L;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
