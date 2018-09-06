/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:34:00 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:34:00 
 * @version V1.0
 */
public class FunctionInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	private String value;
	public Attribute getAttr() {
		return attr;
	}
	public String getValue() {
		return value;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
