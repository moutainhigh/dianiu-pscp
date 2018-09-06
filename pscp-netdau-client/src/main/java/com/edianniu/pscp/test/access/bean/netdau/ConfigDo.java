/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:26:23 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:26:23
 * @version V1.0
 */
public class ConfigDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private DefaultAttrDo attr;
	private String period;
	
	public DefaultAttrDo getAttr() {
		return attr;
	}
	public void setAttr(DefaultAttrDo attr) {
		this.attr = attr;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
