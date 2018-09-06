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
public class ReportConfigDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private DefaultAttrDo attr;
	private String report_ack;

	public DefaultAttrDo getAttr() {
		return attr;
	}

	public void setAttr(DefaultAttrDo attr) {
		this.attr = attr;
	}

	public String getReport_ack() {
		return report_ack;
	}

	public void setReport_ack(String report_ack) {
		this.report_ack = report_ack;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
