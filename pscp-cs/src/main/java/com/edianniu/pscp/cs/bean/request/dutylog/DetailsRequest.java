package com.edianniu.pscp.cs.bean.request.dutylog;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房值班日志详情
 * @author zhoujianjian
 * @date 2017年10月30日 下午6:51:06
 */
@JSONMessage(messageCode = 1002142)
public class DetailsRequest extends TerminalRequest{
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
