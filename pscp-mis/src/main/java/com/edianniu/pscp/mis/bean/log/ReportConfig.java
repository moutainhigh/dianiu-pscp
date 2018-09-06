package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月25日 下午5:30:07
 * @version V1.0
 */
public class ReportConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	private Attribute attr;
	private String report_ack;

	public Attribute getAttr() {
		return attr;
	}

	public String getReport_ack() {
		return report_ack;
	}

	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	public void setReport_ack(String report_ack) {
		this.report_ack = report_ack;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
