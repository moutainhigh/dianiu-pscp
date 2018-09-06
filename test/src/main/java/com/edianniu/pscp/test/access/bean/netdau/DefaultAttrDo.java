/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:28:45 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:28:45
 * @version V1.0
 */
public class DefaultAttrDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String operation;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
