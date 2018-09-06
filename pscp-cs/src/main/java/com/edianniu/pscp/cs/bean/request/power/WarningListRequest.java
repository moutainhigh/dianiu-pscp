package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 报警列表
 * @author zhoujianjian
 * @date 2017年12月11日 下午4:12:49
 */
@JSONMessage(messageCode = 1002188)
public final class WarningListRequest extends TerminalRequest{
	
	private int offset;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
