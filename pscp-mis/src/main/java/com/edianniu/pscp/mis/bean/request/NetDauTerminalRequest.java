package com.edianniu.pscp.mis.bean.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.log.CommonInfo;

public class NetDauTerminalRequest extends BaseRequest  {
	private CommonInfo common;
	public CommonInfo getCommon() {
		return common;
	}
	public void setCommon(CommonInfo common) {
		this.common = common;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
