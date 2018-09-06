/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
public class ControlInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	private List<Control> control;
	public Attribute getAttr() {
		return attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public List<Control> getControl() {
		return control;
	}
	public void setControl(List<Control> control) {
		this.control = control;
	}

}
