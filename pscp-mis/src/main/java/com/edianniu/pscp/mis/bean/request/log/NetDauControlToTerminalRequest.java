/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.annotation.MessageHeader;
import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.InstructionInfo;
import com.edianniu.pscp.mis.bean.request.NetDauTerminalRequest;

/**
 * netdau 透传命令响应
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19
 * @version V1.0
 */
@JSONMessage(messageCode = 1003005)
@MessageHeader(type=3)
public final class NetDauControlToTerminalRequest extends NetDauTerminalRequest {
	private InstructionInfo instruction;
	
	public InstructionInfo getInstruction() {
		return instruction;
	}

	public void setInstruction(InstructionInfo instruction) {
		this.instruction = instruction;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	
}
