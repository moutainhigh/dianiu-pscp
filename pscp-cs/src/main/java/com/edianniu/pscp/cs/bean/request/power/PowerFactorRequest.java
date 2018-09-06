package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午2:45:49
 */
@JSONMessage(messageCode = 1002185)
public class PowerFactorRequest extends TerminalRequest{
	
	// 客户线路ID
	private Long lineId;
	
	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
