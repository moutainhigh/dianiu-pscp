package com.edianniu.pscp.mis.bean.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.log.CommonInfo;

import stc.skymobi.bean.AbstractCommonBean;
import stc.skymobi.bean.json.JSONSignal;

public abstract class NetDauResponse extends AbstractCommonBean implements
		JSONSignal {
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
