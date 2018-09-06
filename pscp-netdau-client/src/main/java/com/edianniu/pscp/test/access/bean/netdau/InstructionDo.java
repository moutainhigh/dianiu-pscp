package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午4:12:19 
 * @version V1.0
 */
public class InstructionDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private AttributeDo attr;
	private ControlInfoDo control_info;
	public AttributeDo getAttr() {
		return attr;
	}
	
	public void setAttr(AttributeDo attr) {
		this.attr = attr;
	}

	public ControlInfoDo getControl_info() {
		return control_info;
	}

	public void setControl_info(ControlInfoDo control_info) {
		this.control_info = control_info;
	}
	
}
