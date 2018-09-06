package com.edianniu.pscp.mis.bean.response.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.ReportConfig;
import com.edianniu.pscp.mis.bean.response.NetDauResponse;
/**
 * netdau offline response
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43 
 * @version V1.0
 */
@JSONMessage(messageCode = 2003004)
public final class NetDauOfflineResponse extends NetDauResponse {
	
	private ReportConfig report_config;
	
	public ReportConfig getReport_config() {
		return report_config;
	}
	public void setReport_config(ReportConfig report_config) {
		this.report_config = report_config;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
