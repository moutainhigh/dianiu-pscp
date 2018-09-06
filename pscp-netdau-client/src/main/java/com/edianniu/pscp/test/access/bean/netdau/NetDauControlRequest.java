package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 心跳请求
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:30:06
 * @version V1.0
 */
public class NetDauControlRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private CommonDo common;
	private InstructionDo instruction;

	public CommonDo getCommon() {
		return common;
	}

	public void setCommon(CommonDo common) {
		this.common = common;
	}

	public InstructionDo getInstruction() {
		return instruction;
	}

	public void setInstruction(InstructionDo instruction) {
		this.instruction = instruction;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
