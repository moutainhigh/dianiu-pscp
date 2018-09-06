package com.edianniu.pscp.mis.bean.response.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;
import com.edianniu.pscp.mis.bean.log.ReportInfo;
import com.edianniu.pscp.mis.bean.response.NetDauResponse;
/**
 * netdau report response
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:43 
 * @version V1.0
 */
@JSONMessage(messageCode = 2003002)
public final class NetDauQueryResponse extends NetDauResponse {
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
