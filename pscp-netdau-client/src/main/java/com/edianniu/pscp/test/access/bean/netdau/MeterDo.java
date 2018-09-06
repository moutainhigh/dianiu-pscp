/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:32:22 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

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
public class MeterDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private AttributeDo attr;
	List<FunctionDo> function;
	
	public AttributeDo getAttr() {
		return attr;
	}
	public List<FunctionDo> getFunction() {
		return function;
	}
	public void setAttr(AttributeDo attr) {
		this.attr = attr;
	}
	public void setFunction(List<FunctionDo> function) {
		this.function = function;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
