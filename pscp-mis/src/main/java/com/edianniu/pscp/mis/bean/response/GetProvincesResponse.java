package com.edianniu.pscp.mis.bean.response;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.ProvinceInfo;
@JSONMessage(messageCode = 2002062)
public class GetProvincesResponse extends BaseResponse{
	
	private List<ProvinceInfo> provinces;
	
	public List<ProvinceInfo> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<ProvinceInfo> provinces) {
		this.provinces = provinces;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
	
}
