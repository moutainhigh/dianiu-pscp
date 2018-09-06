/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
public class ControlAckInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	private List<Control> control_ack;
	public Attribute getAttr() {
		return attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public List<Control> getControl_ack() {
		return control_ack;
	}
	public void setControl_ack(List<Control> control_ack) {
		this.control_ack = control_ack;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
