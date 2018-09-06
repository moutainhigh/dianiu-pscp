package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午4:12:19 
 * @version V1.0
 */
public class InstructionAckInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	private ControlAckInfo control_info;
	public Attribute getAttr() {
		return attr;
	}
	
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	public ControlAckInfo getControl_info() {
		return control_info;
	}

	public void setControl_info(ControlAckInfo control_info) {
		this.control_info = control_info;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
