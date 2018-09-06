package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 注册接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:30:06 
 * @version V1.0
 */
public class NetDauReportResponse implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private CommonDo common;
	private ReportConfigDo report_config;
	public CommonDo getCommon() {
		return common;
	}
	
	public void setCommon(CommonDo common) {
		this.common = common;
	}
	
	public ReportConfigDo getReport_config() {
		return report_config;
	}

	public void setReport_config(ReportConfigDo report_config) {
		this.report_config = report_config;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
