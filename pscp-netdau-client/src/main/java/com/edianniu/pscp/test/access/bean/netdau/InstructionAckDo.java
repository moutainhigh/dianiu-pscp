package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午4:12:19 
 * @version V1.0
 */
public class InstructionAckDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private DefaultAttrDo attr;
	private ControlAckInfoDo control_info;
	public DefaultAttrDo getAttr() {
		return attr;
	}
	
	public void setAttr(DefaultAttrDo attr) {
		this.attr = attr;
	}

	public ControlAckInfoDo getControl_info() {
		return control_info;
	}

	public void setControl_info(ControlAckInfoDo control_info) {
		this.control_info = control_info;
	}
	
}
