/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
public class ControlInfoDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private AttributeDo attr;
	private List<ControlDo> control;
	public AttributeDo getAttr() {
		return attr;
	}
	public void setAttr(AttributeDo attr) {
		this.attr = attr;
	}
	public List<ControlDo> getControl() {
		return control;
	}
	public void setControl(List<ControlDo> control) {
		this.control = control;
	}

}
