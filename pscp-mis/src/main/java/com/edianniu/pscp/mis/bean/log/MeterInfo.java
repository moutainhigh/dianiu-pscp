/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:32:22 
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
 * @date 2017年9月22日 下午3:32:22 
 * @version V1.0
 */
public class MeterInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	List<FunctionInfo> function;
	
	public Attribute getAttr() {
		return attr;
	}
	public List<FunctionInfo> getFunction() {
		return function;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public void setFunction(List<FunctionInfo> function) {
		this.function = function;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
