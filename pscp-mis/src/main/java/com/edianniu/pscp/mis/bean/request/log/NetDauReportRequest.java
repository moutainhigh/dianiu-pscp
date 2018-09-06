/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.log.ReportInfo;
import com.edianniu.pscp.mis.bean.request.NetDauRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * netdau report
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1003001)
public final class NetDauReportRequest extends NetDauRequest {
	private ReportInfo data;
	public ReportInfo getData() {
		return data;
	}
	public void setData(ReportInfo data) {
		this.data = data;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
