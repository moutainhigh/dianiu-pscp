package com.edianniu.pscp.mis.bean.request.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.InstructionAckInfo;
import com.edianniu.pscp.mis.bean.response.NetDauResponse;

/**
 * netdau controlAck response
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43
 * @version V1.0
 */
@JSONMessage(messageCode = 2003005)
public final class NetDauControlAckRequest extends NetDauResponse {
	private InstructionAckInfo instruction;
	
	public InstructionAckInfo getInstruction() {
		return instruction;
	}

	public void setInstruction(InstructionAckInfo instruction) {
		this.instruction = instruction;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
